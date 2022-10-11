package com.finder.job.strategy.ua;

import com.finder.job.models.Skill;
import com.finder.job.models.Vacancy;
import com.finder.job.strategy.Strategy;

import java.io.IOException;
import java.util.List;

public class DouStrategy implements Strategy {

    @Override
    public List<Vacancy> getVacancies(String query) throws IOException {
        return null;
    }

    @Override
    public String generateUrl(String query, Integer page) {
        return null;
    }
}
