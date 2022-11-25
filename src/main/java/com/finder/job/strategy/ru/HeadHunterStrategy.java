package com.finder.job.strategy.ru;

import com.finder.job.mapper.VacancyMapperHTML;
import com.finder.job.models.vacancy.SuperJobVacancy;
import com.finder.job.models.vacancy.Vacancy;
import com.finder.job.strategy.Strategy;
import com.finder.job.util.NetworkHelper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HeadHunterStrategy implements Strategy<String> {

    private final NetworkHelper networkHelper;
    private final VacancyMapperHTML<Vacancy, Document, Element> mapper;

    public HeadHunterStrategy() {
        this.networkHelper = new NetworkHelper();
        this.mapper = new HeadHunterMapper();
    }

    @Override
    public List<Vacancy> getVacancies(String position) throws IOException {
        String url = generateUrl(position, null);
        Document document = networkHelper.getPageFromURL(url);
        int pagesCount = mapper.getPagesCount(document);
        List<Vacancy> vacancies = new ArrayList<>();

        if (pagesCount > 0) {
            for (int i = 0; i < pagesCount; i++) {
                String pageUrl = generateUrl(position, i);
                Document page = networkHelper.getPageFromURL(pageUrl);
                vacancies.addAll(mapper.parseVacanciesListFrom(page));
            }
        } else {
            vacancies.addAll(mapper.parseVacanciesListFrom(document));
        }
        return vacancies;
    }

    @Override
    public String generateUrl(String position, Integer page) {
        String SITE = "https://hh.ru/search/vacancy";
        String POSITION_PARAM = "?text=%s";
        if (page != null) {
            String PAGE_PARAM = "&page=%d";
            return SITE + (String.format(POSITION_PARAM, position)) + (String.format(PAGE_PARAM, page));
        } else {
            return SITE + (String.format(POSITION_PARAM, position));
        }
    }

    class HeadHunterMapper implements VacancyMapperHTML<Vacancy, Document, Element> {

        @Override
        public List<Vacancy> parseVacanciesListFrom(Document source) throws IOException {
            Elements rawVacancies = source.select("div.vacancy-serp-content > div > div.serp-item");
            List<Vacancy> vacancies = new ArrayList<>();
            for (Element element : rawVacancies) {
                Vacancy vacancy = mapper.parseVacancyFrom(element);
                vacancies.add(vacancy);
            }
            return vacancies;
        }

        @Override
        public Vacancy parseVacancyFrom(Element element) {
            String title = element.select("div.vacancy-serp-item__layout > div.vacancy-serp-item-body > div.vacancy-serp-item-body__main-info > div > h3").text();
            String company = element.select("div.vacancy-serp-item__layout > div.vacancy-serp-item-body > div.vacancy-serp-item-body__main-info > div.vacancy-serp-item-company > div.vacancy-serp-item__info > div.bloko-v-spacing-container.bloko-v-spacing-container_base-2").text();
            String town = element.select("div.vacancy-serp-item__layout > div.vacancy-serp-item-body > div.vacancy-serp-item-body__main-info > div.vacancy-serp-item-company > div.vacancy-serp-item__info > div.bloko-text").text();
            String description = element.select("div.vacancy-serp-item__layout > div.vacancy-serp-item__info > div.g-user-content").text();
            String salary = element.select("div.vacancy-serp-item__layout > div.vacancy-serp-item-body > div.vacancy-serp-item-body__main-info > div > span").text();
            if (salary.isEmpty()) salary = "з/п не указана";
            String link = element.select("div.vacancy-serp-item__layout > div.vacancy-serp-item-body > div.vacancy-serp-item-body__main-info > div > h3 > span > a").attr("href");
            return new SuperJobVacancy(title, company, town, description, salary, link, "");
        }

        @Override
        public Integer getPagesCount(Document document) {
            Elements pagesList = document.select("div.vacancy-serp-content > div.bloko-gap.bloko-gap_top > div.pager");
            int pagesCount = 0;
            if (!pagesList.isEmpty()) {
                pagesCount = Integer.parseInt(pagesList.text().substring(pagesList.text().indexOf("д") - 2, pagesList.text().indexOf("д")));
            }
            return pagesCount;
        }
    }
}
