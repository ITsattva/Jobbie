package com.finder.job.strategy;

import com.finder.job.models.Skill;
import com.finder.job.models.Vacancy;

import java.io.IOException;
import java.util.List;

public interface Strategy<U> {
    List<Vacancy> getVacancies(String query) throws IOException;
    U generateUrl(String query, Integer page);
}
