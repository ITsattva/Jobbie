package com.finder.job.mapper;

import org.jsoup.nodes.Document;

public interface VacancyMapperHTML<V, D, E> extends VacancyMapper<V, D, E>{
    Integer getPagesCount(D document);
}
