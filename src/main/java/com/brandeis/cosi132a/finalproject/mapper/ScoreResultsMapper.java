package com.brandeis.cosi132a.finalproject.mapper;

import com.brandeis.cosi132a.finalproject.model.Scoreable;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.DefaultResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.mapping.ElasticsearchPersistentEntity;
import org.springframework.data.elasticsearch.core.mapping.ElasticsearchPersistentProperty;
import org.springframework.data.mapping.context.MappingContext;

import java.util.Iterator;

public class ScoreResultsMapper extends DefaultResultMapper {
    public ScoreResultsMapper(MappingContext<? extends ElasticsearchPersistentEntity<?>, ElasticsearchPersistentProperty> mappingContext) {
        super(mappingContext);
    }

    @Override
    public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> _class, Pageable pageable) {
        AggregatedPage<T> resultPage = super.mapResults(response, _class, pageable);
        Iterator<T> it = resultPage.getContent().iterator();
        for (SearchHit hit : response.getHits()) {
            if (hit != null) {
                T next = it.next();
                if (next instanceof  Scoreable) {
                    ((Scoreable) next).setScore(hit.getScore());
                    ((Scoreable) next).setHighlightFields(hit.getHighlightFields());
                }
            }
        }
        return resultPage;
    }

}
