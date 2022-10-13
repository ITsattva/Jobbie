package com.finder.job.strategy.ua;

import com.finder.job.mapper.VacancyMapperHTML;
import com.finder.job.models.vacancy.DjinniVacancy;
import com.finder.job.models.vacancy.Vacancy;
import com.finder.job.strategy.Strategy;
import com.finder.job.util.NetworkHelper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DjinniStrategy implements Strategy<String> {
    private final VacancyMapperHTML<Vacancy, Document, Element> mapper;
    private final NetworkHelper<Object> networkHelper;
    private final String SITE = "https://djinni.co/jobs/";
    private final String POSITION_PARAM = "?primary_keyword=%s";
    private final String PAGE_PARAM = "&page=%d";

    public DjinniStrategy() {
        this.networkHelper = new NetworkHelper<>(Object.class);
        mapper = new DjinniMapper();
    }

    @Override
    public List<Vacancy> getVacancies(String position) throws IOException {//todo refactor duplicated code
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

    class DjinniMapper implements VacancyMapperHTML<Vacancy, Document, Element> {

        @Override
        public List<Vacancy> parseVacanciesListFrom(Document html) throws IOException {
            Elements rawVacancies = html.select("ul.list-unstyled.list-jobs > li");

            List<Vacancy> vacancies = new ArrayList<>();
            for(Element element : rawVacancies) {
                Vacancy vacancy = mapper.parseVacancyFrom(element);
                vacancies.add(vacancy);
            }

            return vacancies;
        }

        @Override
        public Vacancy parseVacancyFrom(Element element) throws IOException {
            String title = element.select("div.list-jobs__title > a.profile > span").text();
            String company = element.select("div.list-jobs__details > div > a").first().text();
            String town = element.select("div.list-jobs__details > div > span.location-text").text();
            String innerLink = element.select("div.list-jobs__title > a.profile").attr("href").substring(6);
            String date = element.select("div.text-date.pull-right").text();
            Element salaryElement = element.select("div.list-jobs__title > span").first();
            String salary = salaryElement != null ? salaryElement.text().replaceAll("[ , ]", "") : "За результатами співбесіди";
            String description = element.select("div.list-jobs__description > p").text();

            return new DjinniVacancy(title, company, town, description, salary, SITE + innerLink, date);
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
