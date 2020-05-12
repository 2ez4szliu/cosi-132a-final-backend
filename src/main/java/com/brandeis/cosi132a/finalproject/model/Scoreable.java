package com.brandeis.cosi132a.finalproject.model;

import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;

import java.util.Map;

/**
 * This interface has score and get highlight function.
 * Class that implements this interface should have a
 * score field and a highlight field
 */
public interface Scoreable {
    float getScore();

    void setScore(float score);

    Map<String, HighlightField> getHighlightFields();

    void setHighlightFields(Map<String, HighlightField> highlightFields);
}
