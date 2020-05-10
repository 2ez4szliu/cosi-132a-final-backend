package com.brandeis.cosi132a.finalproject.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Word2VecServiceImpl implements Word2VecService {

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public double[] sentenceToVector(String sentence) {
        ResponseEntity response = restTemplate.exchange("http://localhost:5000/results?query=" + sentence, HttpMethod.GET, null, String.class);
        Gson gson = new Gson();
        String content = response.getBody().toString();
        JsonObject jsonObject = gson.fromJson(content, JsonObject.class);
        double[] arr = gson.fromJson(jsonObject.get("vector"), double[].class);
        return arr;
    }

}
