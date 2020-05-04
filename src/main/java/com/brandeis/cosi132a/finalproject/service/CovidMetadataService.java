package com.brandeis.cosi132a.finalproject.service;

import com.brandeis.cosi132a.finalproject.model.CovidMeta;
import com.brandeis.cosi132a.finalproject.model.Sentence;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CovidMetadataService {

    CovidMeta save(CovidMeta covidMeta);

    Page<CovidMeta> findByTitle(String title, int page);

    Page<CovidMeta> findByAuthors(String author, int page);

    Page<CovidMeta> findByPublishTime(String publishTime, int page);

    Page<CovidMeta> findByPublishTimeGTE(String publishTime, int page);

    /**
     * Search by text fields(author, title, abstract and body text)
     *
     * @param text
     * @return
     */
    Page<CovidMeta> findByText(String text, int page);

    List<Sentence> findSentence(String base64vector);
}
