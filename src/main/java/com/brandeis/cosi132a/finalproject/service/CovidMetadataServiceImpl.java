package com.brandeis.cosi132a.finalproject.service;

import com.brandeis.cosi132a.finalproject.model.CovidMeta;
import com.brandeis.cosi132a.finalproject.model.Sentence;
import com.brandeis.cosi132a.finalproject.repository.CovidMetaRepository;
import com.brandeis.cosi132a.finalproject.utils.Constants;
import com.brandeis.cosi132a.finalproject.utils.VectorEncoder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@Service
public class CovidMetadataServiceImpl implements CovidMetadataService {

    @Autowired
    private CovidMetaRepository covidMetaRepository;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private RestClient restClient;

    @Autowired
    private ElasticsearchClient esClient;

    @Override
    public CovidMeta save(CovidMeta covidMeta) {
        return covidMetaRepository.save(covidMeta);
    }

    @Override
    public Page<CovidMeta> findByTitle(String title, int page) {
        SearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("title", title))
                .withHighlightFields(new HighlightBuilder.Field("title"))
                .withPageable(PageRequest.of(page, Constants.RESULTS_PER_PAGE))
                .build();
        return elasticsearchOperations.queryForPage(query, CovidMeta.class);
    }

    @Override
    public Page<CovidMeta> findByAuthors(String author, int page) {
        return covidMetaRepository.findByAuthors(author, PageRequest.of(page, Constants.RESULTS_PER_PAGE));
    }

    @Override
    public Page<CovidMeta> findByPublishTime(String publishTime, int page) {
        return covidMetaRepository.findByPublishTime(publishTime, PageRequest.of(page, Constants.RESULTS_PER_PAGE));
    }

    @Override
    public Page<CovidMeta> findByPublishTimeGTE(String publishTime, int page) {
        return covidMetaRepository.findByPublishTimeGreaterThanEqual(publishTime, PageRequest.of(page, Constants.RESULTS_PER_PAGE));
    }

//    public Page<CovidMeta> query(String author, String title, String dateFrom, String dateTo) {
//
//    }

    @Override
    public Page<CovidMeta> findByText(String text, int page) {
        SearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(multiMatchQuery(text)
                        .field("title")
                        .field("authors")
                        .field("textAbstract")
                        .field("bodyText")
                        .type(MultiMatchQueryBuilder.Type.CROSS_FIELDS)
                )
                .withHighlightFields(
                        new HighlightBuilder.Field("title"),
                        new HighlightBuilder.Field("authors"),
                        new HighlightBuilder.Field("textAbstract"),
                        new HighlightBuilder.Field("bodyText")
                )
                .withPageable(PageRequest.of(page, Constants.RESULTS_PER_PAGE))
                .build();
        return elasticsearchOperations.queryForPage(query, CovidMeta.class);
    }

    @Override
    public List<Sentence> findSentence(String base64vector) {
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
                "}";
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
        List<Sentence> results = new ArrayList<>();
        for (int i = 0; i < hits.size(); i++) {
            JsonObject next = hits.get(i).getAsJsonObject();
            Sentence sentence = gson.fromJson(next.get("_source"), Sentence.class);
            float score = next.get("_score").getAsFloat();
            sentence.setScore(score);
            results.add(sentence);
        }
        return results;
    }

}
