package com.brandeis.cosi132a.finalproject.model;

public class ResultWrapper {
    private CovidMeta covidMeta;
    private Sentence sentence;

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
}
