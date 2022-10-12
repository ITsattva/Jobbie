package com.finder.job.strategy.ua;

import com.finder.job.mapper.VacancyMapperHTML;
import com.finder.job.models.Vacancy;
import com.finder.job.strategy.Strategy;
import com.finder.job.util.NetworkHelper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class WorkUaStrategy implements Strategy<String> {
    private final VacancyMapperHTML<Vacancy, Document, Element> mapper;
    private final NetworkHelper<Object> networkHelper;
    private final String SITE = "https://www.work.ua/ru/";
    private final String POSITION_PARAM = "jobs-%s/";
    private final String PAGE_PARAM = "?page=%d";

    public WorkUaStrategy() {
        this.networkHelper = new NetworkHelper<>(Object.class);
        mapper = new WorkUaMapper();
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
    public String generateUrl(String area, Integer page){
        if(page != null) {
            return SITE + (String.format(POSITION_PARAM, area)) + (String.format(PAGE_PARAM, page));
        } else {
            return SITE + (String.format(POSITION_PARAM, area));
        }
    }

    private class WorkUaMapper implements VacancyMapperHTML<Vacancy, Document, Element> {
        @Override
        public List<Vacancy> parseVacanciesListFrom(Document html) throws IOException {
            Elements rawVacancies = html.select("div.card.card-hover.card-visited.wordwrap.job-link");

            List<Vacancy> vacancies = new ArrayList<>();
            for(Element element : rawVacancies) {
                Vacancy vacancy = mapper.parseVacancyFrom(element);
                vacancies.add(vacancy);
            }

            return vacancies;
        }

        @Override
        public Vacancy parseVacancyFrom(Element element){
            String title = element.select("h2 > a").text().replace(" \uFEFF", "");
            String company = element.select("div.add-top-xs > span > b").text();
            Element distanceFromCenter = element.select("span.distance-block.unclickable-inner-block.text-blue-link").last();
            String town = "";
            if(distanceFromCenter == null) {
                town = element.select("div.add-top-xs > span").last().text();
            } else {
                int size = element.select("div.add-top-xs > span").size();
                String distance = element.select("div.add-top-xs > span").get(size-1).text();
                String townRaw = element.select("div.add-top-xs > span").get(size-3).text();
                town = townRaw + ", " + distance;
            }
            String innerLink = element.select("div > h2 > a").attr("href").substring(4);
            String date = element.select("div.row > div.col-sm-push-7.col-sm-5.col-xs-12.add-top > div > span").text();
            Element salaryElement = element.select("div > b").first();
            String salary = salaryElement != null ? salaryElement.text().replaceAll("[ , ]", "") : "По результатам собеседования";
            String description = element.select("p").text();

            return new Vacancy(title, company, town, description, salary, SITE + innerLink, date);
        }

        @Override
        public Integer getPagesCount(Document document){
            Elements pagesList = document.select("nav > ul.pagination.hidden-xs > li");
            int pagesCount = 0;
            if(pagesList.size() != 0) {
                pagesCount = Integer.parseInt(pagesList.get(pagesList.size()-2).text());
            }

            return pagesCount;
        }
    }
}
