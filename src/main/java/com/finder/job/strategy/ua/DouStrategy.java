package com.finder.job.strategy.ua;

import com.finder.job.mapper.VacancyMapperHTML;
import com.finder.job.models.vacancy.DouVacancy;
import com.finder.job.models.vacancy.Vacancy;
import com.finder.job.strategy.Strategy;
import com.finder.job.util.NetworkHelper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DouStrategy implements Strategy<String> {
    private final VacancyMapperHTML<Vacancy, Document, Element> mapper;
    private final NetworkHelper<Object> networkHelper;
    private final String SITE = "https://jobs.dou.ua/vacancies/";
    private final String POSITION_PARAM = "?search=%s";
    private final String PAGE_PARAM = "&page=%d";

    public DouStrategy() {
        this.networkHelper = new NetworkHelper<>(Object.class);
        mapper = new DouMapper();
    }

    @Override
    public List<Vacancy> getVacancies(String position) throws IOException {
        String url = generateUrl(position, null);
        Document document = networkHelper.getPageFromURL(url);
        int pagesCount = mapper.getPagesCount(document);
        List<Vacancy> vacancies = new ArrayList<>();

        if(pagesCount > 0) {
            for(int i = 1; i <= pagesCount; i++){
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
        if(page != null) {
            return SITE + (String.format(POSITION_PARAM, position)) + (String.format(PAGE_PARAM, page));
        } else {
            return SITE + (String.format(POSITION_PARAM, position));
        }
    }

    class DouMapper implements VacancyMapperHTML<Vacancy, Document, Element> {

        @Override
        public List<Vacancy> parseVacanciesListFrom(Document html) throws IOException {
            //#vacancyListId > ul > li:nth-child(11)
            Elements rawVacancies = html.select("#vacancyListId > ul > li");

            System.out.println("Elements size: " + rawVacancies.size());
            List<Vacancy> vacancies = new ArrayList<>();
            for(Element element : rawVacancies) {
                Vacancy vacancy = mapper.parseVacancyFrom(element);
                vacancies.add(vacancy);
            }

            return vacancies;
        }

        @Override
        public Vacancy parseVacancyFrom(Element element) {
            String title = element.select("div > div.title > a").text();
            String company = element.select("div > div.title > strong > a").first().text();
            String town = element.select("div > div.title > span").text();
            String innerLink = element.select("div > div.title > a").attr("href");
            Element dateElement = element.select("div > div.date").first();
            String date = dateElement != null ? dateElement.text() : "1 сек. назад";
            Element salaryElement = element.select("div > div.title > span.salary").first();
            String salary = salaryElement != null ? salaryElement.text().replaceAll("[ , ]", "") : "За результатами співбесіди";
            String description = element.select("div > div.sh-info").text().replaceAll("[ , ]", "");

            return new DouVacancy(title, company, town, description, salary, innerLink, date);
        }

        @Override
        public Integer getPagesCount(Document document) {
            Elements pagesList = document.select("ul.pagination.pagination_with_numbers > li");
            int pagesCount = 0;
            if(pagesList.size() != 0) {
                pagesCount = Integer.parseInt(pagesList.get(pagesList.size()-2).text());
            }

            return pagesCount;
        }
    }
}
