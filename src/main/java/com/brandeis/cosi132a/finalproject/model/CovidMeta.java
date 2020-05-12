package com.brandeis.cosi132a.finalproject.model;

import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;
import java.util.Map;

/**
 * This class represents a document from the metadata.csv file
 */
@Document(indexName = "covid_index", type = "_doc")
public class CovidMeta implements Scoreable{
//covid_index & covid_embedding_index
    @Id
    private String id;
    private String paperId;
    private String sha;
    private String title;
    private String pmcid;
    private List<String> textAbstract;
    private List<String> bodyText;
    private List<String> authors;
    private String publishTime;
    private String url;
    private float score;
    private Map<String, HighlightField> highlightFields;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getPmcid() {
        return pmcid;
    }

    public void setPmcid(String pmcid) {
        this.pmcid = pmcid;
    }

    public List<String> getTextAbstract() {
        return textAbstract;
    }

    public void setTextAbstract(List<String> textAbstract) {
        this.textAbstract = textAbstract;
    }

    public List<String> getBodyText() {
        return bodyText;
    }

    public void setBodyText(List<String> bodyText) {
        this.bodyText = bodyText;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    @Override
    public String toString() {
        return "CovidMeta{" +
                "id='" + id + '\'' +
                ", paperId='" + paperId + '\'' +
                ", sha='" + sha + '\'' +
                ", title='" + title + '\'' +
                ", pmcid='" + pmcid + '\'' +
                ", textAbstract=" + textAbstract +
                ", bodyText=" + bodyText +
                ", authors=" + authors +
                ", publishTime='" + publishTime + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

}
