package com.brandeis.cosi132a.finalproject.service;

public interface Word2VecService {
    double[] sentenceToVector(String sentence);

    String plotTrendImage(String query);
}
