package com.finder.job.mapper;

import com.finder.job.models.vacancy.Vacancy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public interface VacancyMapper<V, D, E> {
    List<V> parseVacanciesListFrom(D html) throws IOException;
    Vacancy parseVacancyFrom(E element) throws IOException;
}
