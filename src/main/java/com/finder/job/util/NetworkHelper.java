package com.finder.job.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

/**
 * Helper for working with network
 */
@Component
public class NetworkHelper {
    private final ObjectMapper objectMapper;

    public NetworkHelper() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     *
     * @param url defines source
     * @return DOM object of HTML page
     * @throws IOException when can't connect to the page
     */
    public Document getPageFromURL(String url) throws IOException {
        return Jsoup.connect(url)
                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                .referrer("https://www.google.com")
                .get();
    }

//    public T getPOJOfromUrl(String url) throws IOException {//todo later
//        URL link = new URL(url);
//        T pojo = objectMapper.readValue(url, type);
//
//        return pojo;
//    }

}
