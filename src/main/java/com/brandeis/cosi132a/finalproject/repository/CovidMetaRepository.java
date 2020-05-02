package com.brandeis.cosi132a.finalproject.repository;

import com.brandeis.cosi132a.finalproject.model.CovidMeta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CovidMetaRepository extends ElasticsearchRepository<CovidMeta, String> {
    //ElasticsearchRepository<Domain class, ID type>

    CovidMeta save(CovidMeta covidMeta);

    Page<CovidMeta> findByTitle(String title, Pageable pageable);

    Page<CovidMeta> findByAuthors(String authors, Pageable pageable);

    Page<CovidMeta> findByPublishTime(String publishTime, Pageable pageable);

    Page<CovidMeta> findByPublishTimeGreaterThanEqual(String publishTime, Pageable pageable);
}
