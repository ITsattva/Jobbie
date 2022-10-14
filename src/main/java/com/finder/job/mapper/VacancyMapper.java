package com.finder.job.mapper;

import com.finder.job.models.vacancy.Vacancy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * Interface for mapping vacancies from REST API
 *
 * @param <V> vacancy parameter
 * @param <D> from where we'll get list of vacancies
 * @param <E> one element of DOM/JsonObject which will be mapped into Vacancy
 */
@Component
public interface VacancyMapper<V, D, E> {
    /**
     * Invokes parseVacancyFrom() method on each E element, from D source
     * @param source can be HTML page or API
     * @return list of vacancies
     * @throws IOException when can't receive an element or page
     */
    List<V> parseVacanciesListFrom(D source) throws IOException;

    /**
     * Get a vacancy from an element of JSON/JacksonElement
     * @param element defines JSON or JacksonElement
     * @return Vacancy object
     * @throws IOException when can't receive an element of JSON or JacksonElement
     */
    V parseVacancyFrom(E element) throws IOException;
}
