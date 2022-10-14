package com.finder.job.models.vacancy;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 /**
 * implementation of Vacancy for Rabota.ua site
 * This entity is needed because standard API doesn't give us any links of company
 */
@NoArgsConstructor
public class RabotaUaVacancy extends Vacancy {
    public RabotaUaVacancy(String title, String company, String town, String description, String salary, String link, String date) {
        super(title, company, town, description, salary, link, date);
    }

    /**
     * At the very beginning, when vacancy from API had been parsed,
     * we received a link for the next API query when it's needed.
     * In case if we don't do it in this way, we'll receive a big problem
     * with a search algorithm.
     *
     * getLink() method performs a query for a real company's link
     *
     * @return - link on company's site
     */
    @Override
    public String getLink(){
        try {
            URL query = new URL(super.getLink());
            return new ObjectMapper().readTree(query).get("contactUrl").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
