package com.brandeis.cosi132a.finalproject.model;

/**
 * This is a wrapper class for a sentence and the document(covidmeta) containing tat sentence
 */
public class ResultWrapper {
    private CovidMeta covidMeta;
    private Sentence sentence;
    private String imgUrl;

    public ResultWrapper(CovidMeta covidMeta, Sentence sentence) {
        this.covidMeta = covidMeta;
        this.sentence = sentence;
    }

    public CovidMeta getCovidMeta() {
        return covidMeta;
    }

    public void setCovidMeta(CovidMeta covidMeta) {
        this.covidMeta = covidMeta;
    }

    public Sentence getSentence() {
        return sentence;
    }

    public void setSentence(Sentence sentence) {
        this.sentence = sentence;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
