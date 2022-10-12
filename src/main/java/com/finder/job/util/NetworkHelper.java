package com.finder.job.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class NetworkHelper<T> {
    private final ObjectMapper objectMapper;
    private final Class<T> type;

    public NetworkHelper(Class<T> type) {
        this.objectMapper = new ObjectMapper();
        this.type = type;
    }

    public Document getPageFromURL(String url) throws IOException {
        return Jsoup.connect(url)
                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                .referrer("https://www.google.com")
                .get();
    }

    public T getPOJOfromUrl(String url) throws IOException {//todo later
        URL link = new URL(url);
        T pojo = objectMapper.readValue(url, type);

        return pojo;
    }

}
