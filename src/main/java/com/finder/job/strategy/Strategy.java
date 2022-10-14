package com.finder.job.strategy;

import com.finder.job.models.vacancy.Vacancy;

import java.io.IOException;
import java.util.List;

/**
 * @param <U> type of link, URL - for API, String - for HTML
 */
public interface Strategy<U> {
    /**
     * Main method of strategy
     * @param query defines a query for searching
     * @return list of vacancies
     * @throws IOException when doesn't able to receive a page
     */
    List<Vacancy> getVacancies(String query) throws IOException;

    /**
     *
     * @param query defines the main string for GET request
     * @param page defines a page parameter for GET request
     * @return U type of link
     */
    U generateUrl(String query, Integer page);

}
