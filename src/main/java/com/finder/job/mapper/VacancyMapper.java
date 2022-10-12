package com.finder.job.mapper;

import com.finder.job.models.Vacancy;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Component
public interface VacancyMapper<V, D, E> {
    List<V> parseVacanciesListFrom(D html) throws IOException;
    Vacancy parseVacancyFrom(E element) throws IOException;
}
