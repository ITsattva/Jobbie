package com.finder.job.strategy;

import com.finder.job.models.vacancy.Vacancy;

import java.io.IOException;
import java.util.List;

public interface Strategy<U> {
    List<Vacancy> getVacancies(String query) throws IOException;
    U generateUrl(String query, Integer page);

}
