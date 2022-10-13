package com.finder.job.strategy.ru;

import com.finder.job.models.vacancy.Vacancy;
import com.finder.job.strategy.Strategy;

import java.io.IOException;
import java.util.List;

public class HeadHunterStrategy implements Strategy {

    @Override
    public List<Vacancy> getVacancies(String query) throws IOException {
        return null;
    }

    @Override
    public String generateUrl(String query, Integer page) {
        return null;
    }
}
