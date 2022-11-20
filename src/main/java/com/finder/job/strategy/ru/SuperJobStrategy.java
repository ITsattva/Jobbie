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

public class SuperJobStrategy implements Strategy<String> {

    private final NetworkHelper networkHelper;
    private final VacancyMapperHTML<Vacancy, Document, Element> mapper;

    public SuperJobStrategy() {
        this.networkHelper = new NetworkHelper();
        this.mapper = new SuperJobMapper();
    }

    @Override
    public List<Vacancy> getVacancies(String position) throws IOException {
        String url = generateUrl(position, null);
        Document document = networkHelper.getPageFromURL(url);
        int pagesCount = mapper.getPagesCount(document);
        List<Vacancy> vacancies = new ArrayList<>();

        if (pagesCount > 0) {
            for (int i = 1; i <= pagesCount; i++) {
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
        String SITE = "https://russia.superjob.ru/vacancy/search/";
        String POSITION_PARAM = "?keywords=%s";
        if (page != null) {
            String PAGE_PARAM = "&page=%d";
            return SITE + (String.format(POSITION_PARAM, position)) + (String.format(PAGE_PARAM, page));
        } else {
            return SITE + (String.format(POSITION_PARAM, position));
        }
    }

    class SuperJobMapper implements VacancyMapperHTML<Vacancy, Document, Element> {

        @Override
        public List<Vacancy> parseVacanciesListFrom(Document source) throws IOException {
            Elements rawVacancies = source.select("div._3cQ7I._1i5QR._1JpIP > div");
            List<Vacancy> vacancies = new ArrayList<>();
            for (Element element : rawVacancies) {
                Vacancy vacancy = mapper.parseVacancyFrom(element);
                if (!vacancy.getTitle().isEmpty()) {
                    vacancies.add(vacancy);
                }
            }
            return vacancies;
        }

        @Override
        public Vacancy parseVacancyFrom(Element element) {
            String title = element.select("div.f-test-search-result-item > div > div > div._3cQ7I.f-test-vacancy-item._3HN9U.OE7Ds.Yo6p2._1ju3J > div._2lp1U._3NBrD._1WtQU > div.HA2Nf > div._3NBrD._1WtQU > div.HA2Nf > div > span._3FqEL._2JQOY._1dTXK._3h_V4._1hdbq > a").text();
            String company = element.select("div.f-test-search-result-item > div > div > div._3cQ7I.f-test-vacancy-item._3HN9U.OE7Ds.Yo6p2._1ju3J > div._2lp1U._3NBrD._1WtQU > div.HA2Nf > " +
                                            "div._3cQ7I._1Ac04._21flV > div._1h4U2._23y_P._2yUp4 > div.HA2Nf > div._3cQ7I._2EaO5.U10QV._21flV > span._3nMqD.f-test-text-vacancy-item-company-name._1WfLA._1dTXK._3h_V4._2hDjk._2ICq8 > a").text();
            String town = element.select("div.f-test-search-result-item > div > div > div._3cQ7I.f-test-vacancy-item._3HN9U.OE7Ds.Yo6p2._1ju3J > div._2lp1U._3NBrD._1WtQU > div.HA2Nf > " +
                                         "div._3cQ7I._1Ac04._21flV > div._1h4U2._23y_P._2yUp4 > div.HA2Nf > span > div > div").text();
            String description = element.select("div.f-test-search-result-item > div > div > div._3cQ7I.f-test-vacancy-item._3HN9U.OE7Ds.Yo6p2._1ju3J > div._2lp1U._3NBrD._1WtQU > div.HA2Nf > " +
                                                "div._2d_Of._3NBrD._1WtQU > div.HA2Nf > span._22Sdf._1dTXK._3h_V4._2hDjk._2ICq8").text();
            String salary = element.select("div.f-test-search-result-item > div > div > div._3cQ7I.f-test-vacancy-item._3HN9U.OE7Ds.Yo6p2._1ju3J > div._2lp1U._3NBrD._1WtQU > div.HA2Nf > " +
                                           "div._3NBrD._1WtQU > div.HA2Nf > div._3cQ7I.U10QV._1nWlo._21flV > div._3cQ7I.f-test-text-company-item-salary.Brn06._3NkNM._2wBfM.EeLgh").text();
            String link = "https://superjob.ru" + element.select("div.f-test-search-result-item > div > div > div._3cQ7I.f-test-vacancy-item._3HN9U.OE7Ds.Yo6p2._1ju3J > div._2lp1U._3NBrD._1WtQU > div.HA2Nf > div._3NBrD._1WtQU > div.HA2Nf > div > span._3FqEL._2JQOY._1dTXK._3h_V4._1hdbq > a").attr("href");
            String date = element.select("div.f-test-search-result-item > div > div > div._3cQ7I.f-test-vacancy-item._3HN9U.OE7Ds.Yo6p2._1ju3J > div._2lp1U._3NBrD._1WtQU > div.HA2Nf > div._3cQ7I._3nFt9._3bJZe > span").text();
            return new SuperJobVacancy(title, company, town, description, salary, link, date);
        }

        @Override
        public Integer getPagesCount(Document document) {
            Elements pagesList = document.select("div._3cQ7I._9mI07._3T5uq._2lLER._1oLBB._3Sc4g._1JpIP > a > span > span");
            int pagesCount = 0;
            if (!pagesList.isEmpty()) {
                pagesCount = Integer.parseInt(pagesList.text().substring(pagesList.text().indexOf(" Д") - 2, pagesList.text().indexOf(" Д")).trim());
            }
            return pagesCount;
        }
    }
}
