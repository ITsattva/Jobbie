package com.finder.job.strategy.ua;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finder.job.mapper.VacancyMapper;
import com.finder.job.models.vacancy.RabotaUaVacancy;
import com.finder.job.models.vacancy.Vacancy;
import com.finder.job.pojo.RabotaUaPOJO;
import com.finder.job.pojo.RabotaUaPOJO.VacancyShortDto;
import com.finder.job.strategy.Strategy;
import com.finder.job.util.NetworkHelper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RabotaUaStrategy implements Strategy<URL> {
    private final VacancyMapper<Vacancy, RabotaUaPOJO, VacancyShortDto> vacancyMapper;
    private final ObjectMapper objectMapper;
    private final NetworkHelper<RabotaUaPOJO> networkHelper;
    private final String API = "https://ua-api.rabota.ua/";
    private final String POSITION_PARAM = "vacancy/search?keyWords=%s";
    //every additional word should be started with %20
    private final String ADDITIONAL_WORDS_PARAM = "&additionalKeywords=%s";
    private final String ID_PARAM = "vacancy?id=%d";


    public RabotaUaStrategy() {
        vacancyMapper = new RabotaUaMapper();
        networkHelper = new NetworkHelper<>(RabotaUaPOJO.class);
        objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public RabotaUaPOJO getResponse(String query) throws IOException {
        URL url = generateUrl(query, null);
        RabotaUaPOJO pojo = objectMapper.readValue(url, RabotaUaPOJO.class);

        return pojo;
    }

    @Override
    public List<Vacancy> getVacancies(String query) throws IOException {
        RabotaUaPOJO pojo = getResponse(query);
        List<Vacancy> vacancies = vacancyMapper.parseVacanciesListFrom(pojo);

        return vacancies;
    }

    @Override
    public URL generateUrl(String query, Integer page) {
        try {
            return new URL(API + (String.format(POSITION_PARAM, query)));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private class RabotaUaMapper implements VacancyMapper<Vacancy, RabotaUaPOJO, VacancyShortDto> {

        @Override
        public List<Vacancy> parseVacanciesListFrom(RabotaUaPOJO pojo) throws IOException {
            List<Vacancy> vacancies = new ArrayList<>();

            for(VacancyShortDto dto : pojo.getDocuments()){
                Vacancy vacancy = parseVacancyFrom(dto);
                vacancies.add(vacancy);
            }

            return vacancies;
        }

        @Override
        public Vacancy parseVacancyFrom(VacancyShortDto element) throws IOException {
            Vacancy vacancy = new RabotaUaVacancy();
            vacancy.setTitle(element.getName());
            vacancy.setCompany(element.getCompanyName());
            vacancy.setDate(element.getDateTxt());
            vacancy.setDescription(element.getShortDescription().trim());
            Integer salary = element.getSalary();
            vacancy.setSalary(salary == 0 ? "По результатам собеседования" : salary.toString());//todo from to
            vacancy.setTown(element.getCityName());
            vacancy.setLink(API + (String.format(ID_PARAM, element.getId())));//todo dangerous moment;

            return vacancy;
        }
    }
}
