package com.finder.job.mapper;

import com.finder.job.models.Vacancy;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface VacancyMapper {
    List<Vacancy> parseHtmlIntoVacancies(Document html);
    Vacancy parseVacancyFromElement(Element element);
    Integer getPagesCount(Document document);
}
