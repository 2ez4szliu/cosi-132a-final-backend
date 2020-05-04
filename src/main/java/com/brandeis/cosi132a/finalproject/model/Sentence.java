package com.brandeis.cosi132a.finalproject.model;

import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Map;

@Document(indexName = "sentence_embedding_index", type = "_doc")
public class Sentence implements Scoreable {

    private String id;
    private String text;
    private double[] vector;
    private float score;
    private Map<String, HighlightField> highlightFields;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double[] getVector() {
        return vector;
    }

    public void setVector(double[] vector) {
        this.vector = vector;
    }

    @Override
    public float getScore() {
        return score;
    }

    @Override
    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public Map<String, HighlightField> getHighlightFields() {
        return highlightFields;
    }

    @Override
    public void setHighlightFields(Map<String, HighlightField> highlightFields) {
        this.highlightFields = highlightFields;
    }
}
