package com.finder.job.strategy;

import com.finder.job.models.Vacancy;

import java.util.List;

public interface Strategy {
    List<Vacancy> getVacanciesFromSite(String url);

}
