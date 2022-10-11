package com.finder.job.strategy.ua;

import com.finder.job.models.Skill;
import com.finder.job.models.Vacancy;
import com.finder.job.strategy.Strategy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorkUaStrategy implements Strategy {
    private final String SITE = "https://www.work.ua/ru/";
    private final String LANGUAGE = "jobs-%s/";
    private final String PAGE = "?page=%d";

    @Override
    public List<Vacancy> getVacanciesFromSite() {
        return null;//todo this is a stub
    }

    public List<Vacancy> getVacanciesFromSite(String area, String seniority, Skill[] skills) throws IOException {
        String url = generateUrl(area, seniority, skills, null);
        Document document = getResponseFromURL(url);
        int pagesCount = getPagesCount(document);
        List<Vacancy> vacancies = new ArrayList<>();

        if(pagesCount > 0) {
            for(int i = 1; i <= pagesCount; i++){
                String pageUrl = generateUrl(area, seniority, skills, i);
                Document page = getResponseFromURL(pageUrl);
                vacancies.addAll(parseHtmlIntoVacancies(page));
            }
        } else {
            vacancies.addAll(parseHtmlIntoVacancies(document));
        }

        return vacancies;
    }

    public String generateUrl(String area, String seniority, Skill[] skills, Integer page){
        if(page != null) {
            return SITE + (String.format(LANGUAGE, area)) + (String.format(PAGE, page));
        } else {
            return SITE + (String.format(LANGUAGE, area));
        }
    }

    public Document getResponseFromURL(String url) throws IOException {
        return Jsoup.connect(url)
                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                .referrer("http://www.google.com")
                .get();
    }

    public List<Vacancy> parseHtmlIntoVacancies(Document html) throws IOException {
        Elements rawVacancies = html.select("div.card.card-hover.card-visited.wordwrap.job-link");

        List<Vacancy> vacancies = new ArrayList<>();
        for(Element element : rawVacancies) {
            Vacancy vacancy = parseVacancyFromElement(element);
            vacancies.add(vacancy);
        }

        return vacancies;
    }

    public Integer getPagesCount(Document document){
        Elements pagesList = document.select("nav > ul.pagination.hidden-xs > li");
        int pagesCount = 0;
        if(pagesList.size() != 0) {
            pagesCount = Integer.parseInt(pagesList.get(pagesList.size()-2).text());
        }

        return pagesCount;
    }

    public Vacancy parseVacancyFromElement(Element element){
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
}
