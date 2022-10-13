package com.finder.job.models.vacancy;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@NoArgsConstructor
public class RabotaUaVacancy extends Vacancy {
    public RabotaUaVacancy(String title, String company, String town, String description, String salary, String link, String date) {
        super(title, company, town, description, salary, link, date);
    }

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
