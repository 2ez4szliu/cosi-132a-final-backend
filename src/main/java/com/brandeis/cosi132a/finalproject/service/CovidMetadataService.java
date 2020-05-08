package com.brandeis.cosi132a.finalproject.service;

import com.brandeis.cosi132a.finalproject.model.CovidMeta;
import com.brandeis.cosi132a.finalproject.model.ResultWrapper;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CovidMetadataService {

    Page<CovidMeta> query(String text,
                          String title,
                          List<String> authors,
                          String dateFrom,
                          String dateTo,
                          int page);

    List<ResultWrapper> findSentence(String base64vector);

    CovidMeta findByID(String id);
}
