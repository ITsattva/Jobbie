package com.finder.job.strategy;

import com.finder.job.models.Skill;
import com.finder.job.models.Vacancy;

import java.io.IOException;
import java.util.List;

public interface Strategy {
    List<Vacancy> getVacancies(String query) throws IOException;
    String generateUrl(String query, Integer page);
}
