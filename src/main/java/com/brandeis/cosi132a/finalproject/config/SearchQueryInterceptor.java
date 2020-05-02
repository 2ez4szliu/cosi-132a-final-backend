package com.brandeis.cosi132a.finalproject.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SearchQueryInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(SearchQueryInterceptor.class);

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {
        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) {
        //
        String method = request.getMethod();
        String path = request.getRequestURI();
        String query = request.getQueryString();
        int status = response.getStatus();
        logger.info("[METHOD: " + method + "]"
                + " [URI: " + path + "]"
                + " [QUERY: " + query + "]"
                + " [STATUS: " + status + "]");
    }
}
