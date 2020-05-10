package com.brandeis.cosi132a.finalproject.service;

import com.brandeis.cosi132a.finalproject.model.CovidMeta;
import com.brandeis.cosi132a.finalproject.model.ResultWrapper;
import com.brandeis.cosi132a.finalproject.model.Sentence;
import com.brandeis.cosi132a.finalproject.utils.Constants;
import com.brandeis.cosi132a.finalproject.utils.VectorEncoder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Service
public class CovidMetadataServiceImpl implements CovidMetadataService {

    private static final int RESULTS_NUM = Constants.RESULTS_PER_PAGE;
    private static final int SLOP = Constants.PHRASE_SLOP;
    private static final float BOOST = 2;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private RestClient restClient;

    @Override
    public Page<CovidMeta> query(String text,
                                 String title,
                                 List<String> authors,
                                 String dateFrom,
                                 String dateTo,
                                 int page) {
        System.out.println(dateFrom + " " + dateTo);
        System.out.println("Authors: " + authors);
        if (text == null && authors == null && title == null && dateFrom == null && dateTo == null)
            throw new IllegalArgumentException("At least one field must be non-empty.");
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();

        QueryBuilder textQuery = text == null ?
                matchAllQuery() : new BoolQueryBuilder()
                .should(matchQuery("title", text).fuzziness(Fuzziness.AUTO).boost(BOOST))
                .should(matchQuery("authors", text).fuzziness(Fuzziness.AUTO))
                .should(matchQuery("textAbstract", text).fuzziness(Fuzziness.AUTO))
                .should(matchQuery("bodyText", text).fuzziness(Fuzziness.AUTO))
                .minimumShouldMatch(1);
        QueryBuilder authorsQuery = (authors == null || authors.size() == 0) ? matchAllQuery() : matchQuery("authors", authors).fuzziness(Fuzziness.AUTO).operator(Operator.AND);
        System.out.println(dateFrom +  "  " + dateTo);
        QueryBuilder dateQuery;;
        if(dateFrom != null && dateFrom.length() > 0) {
            if(dateTo != null && dateTo.length() > 0) {
                dateQuery = rangeQuery("publishTime").gte(dateFrom).lte(dateTo);
            } else {
                dateQuery = rangeQuery("publishTime").gte(dateFrom);
            }
        } else {
            if(dateTo != null && dateTo.length() > 0) {
                dateQuery = rangeQuery("publishTime").lte(dateTo);
            } else {
                dateQuery = matchAllQuery();
            }
        }
        QueryBuilder boolQueryBuilder = boolQuery()
                .should(textQuery)
                .must(authorsQuery)
                .must(dateQuery)
                .minimumShouldMatch(1);
        builder = builder.withQuery(boolQueryBuilder);
        builder.withPageable(PageRequest.of(page, RESULTS_NUM));
        return elasticsearchOperations.queryForPage(builder.build(), CovidMeta.class);
    }


    @Override
    public List<ResultWrapper> findSentence(String base64vector) {
        String decoded_url = VectorEncoder.urlBase64Decode(base64vector);
        double[] vector = VectorEncoder.convertBase64ToArray(decoded_url);
        String body = "{\n" +
                "\t\"query\": {\n" +
                "  \"script_score\": {\n" +
                "    \"query\": {\"match_all\": {}},\n" +
                "    \"script\": {\n" +
                "      \"source\": \"cosineSimilarity(params.query_vector, 'vector') + 1.0\",\n" +
                "      \"params\": {\"query_vector\":" + Arrays.toString(vector) +
                "\t\t}\n" +
                "\t\t}\n" +
                "    }\n" +
                "  }\n" +
                "}" + "\n";
        String resBody = null;

        try {
            Response response = restClient.performRequest("GET", "/sentence_embedding_index/_search", Collections.emptyMap(), new NStringEntity(body, ContentType.APPLICATION_JSON));
            resBody = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .create();
        JsonObject jsonObject = gson.fromJson(resBody, JsonObject.class);
        JsonArray hits = jsonObject.get("hits").getAsJsonObject().get("hits").getAsJsonArray();
        List<ResultWrapper> results = new ArrayList<>();
        for (int i = 0; i < hits.size(); i++) {
            JsonObject next = hits.get(i).getAsJsonObject();
            Sentence sentence = gson.fromJson(next.get("_source"), Sentence.class);
            float score = next.get("_score").getAsFloat();
            sentence.setScore(score);
            CovidMeta covidMeta = findByID(sentence.getId());
            results.add(new ResultWrapper(covidMeta, sentence));
        }
        return results;
    }

    @Override
    public CovidMeta findByID(String id) {
        String resBody = null;
        try {
            Response response = restClient.performRequest("GET", "/covid_index/_doc/" + id, Collections.emptyMap(), new NStringEntity("", ContentType.APPLICATION_JSON));
            resBody = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .create();
        JsonObject jsonObject = gson.fromJson(resBody, JsonObject.class);
        return gson.fromJson(jsonObject.get("_source"), CovidMeta.class);
    }

}
