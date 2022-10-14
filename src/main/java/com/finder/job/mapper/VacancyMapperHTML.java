package com.finder.job.mapper;

import org.jsoup.nodes.Document;

/**
 * Interface for HTML variant of mapper
 *
 * @param <V> - vacancy parameter
 * @param <D> - from where we'll get list of vacancies
 * @param <E> - one element of DOM/JsonObject which will be mapped into Vacancy
 */
public interface VacancyMapperHTML<V, D, E> extends VacancyMapper<V, D, E>{

    /**
     *
     * @param document - Whole page of html DOM
     * @return - must return a number of pages on from html DOM with vacancies
     */
    Integer getPagesCount(D document);
}
