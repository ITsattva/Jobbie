package com.finder.job.strategy.ua;

import com.finder.job.mapper.VacancyMapper;
import com.finder.job.models.Skill;
import com.finder.job.models.Vacancy;
import com.finder.job.strategy.Strategy;
import lombok.NoArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;

public class RabotaUaStrategy implements Strategy {
    private final VacancyMapper mapper;
    private final String API = "https://ua-api.rabota.ua/vacancy/search?";
    private final String AREA_PARAM = "keyWords=%s";
    private final String ADDITIONAL_WORDS_PARAM = "&additionalKeywords=%s";
    //every additional word should be started with %20


    public RabotaUaStrategy() {
        mapper = new RabotaUaMapper();
    }

    @Override
    public List<Vacancy> getVacancies(String query) throws IOException {
        return null;
    }

    @Override
    public String generateUrl(String query, Integer page) {
        return null;
    }


    private static class RabotaUaMapper implements VacancyMapper {

        @Override
        public List<Vacancy> parseHtmlIntoVacancies(Document html) {
            return null;
        }

        @Override
        public Vacancy parseVacancyFromElement(Element element) {
            return null;
        }

        @Override
        public Integer getPagesCount(Document document) {
            return null;
        }
    }
}
