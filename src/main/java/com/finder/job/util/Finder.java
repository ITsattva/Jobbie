package com.finder.job.util;

import com.finder.job.models.vacancy.Vacancy;
import com.finder.job.strategy.Strategy;
import com.finder.job.strategy.ru.HeadHunterStrategy;
import com.finder.job.strategy.ua.*;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * Main component for handling strategies of getting vacancies
 */
@Component
@NoArgsConstructor
@Log4j2
public class Finder {
//    private final List<String> uaURLs = new ArrayList<>(Arrays.asList(
//            "Djinni",
//            "DOU",
//            "WorkUa",
//            "RabotaUa",
//            "LinkedIn"
//    ));
    private final List<String> uaURLs = new ArrayList<>(Arrays.asList(
//            "Djinni",
            "WorkUa",
            "RabotaUa",
            "DOU"
    ));
    private final List<String> ruURLs = new ArrayList<>(Arrays.asList(
            "HeadHunter"
    ));

    private Strategy strategy;

    private void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Method tunes up a strategy which should be used for receiving
     * @param source defines which site will be used
     */
    private void tuneUpStrategy(String source) {
        switch (source) {
            case "Djinni" -> setStrategy(new DjinniStrategy());
            case "DOU" -> setStrategy(new DouStrategy());
            case "WorkUa" -> setStrategy(new WorkUaStrategy());
            case "RabotaUa" -> setStrategy(new RabotaUaStrategy());
            case "LinkedIn" -> setStrategy(new LinkedInStrategy());
            case "HeadHunter" -> setStrategy(new HeadHunterStrategy());
            default -> throw new IllegalArgumentException("Unknown source has been received from parameters");
        }
    }

    /**
     *
     * @param region defines a country
     * @param query defines a query of searching
     * @return a list of vacancies
     * @throws IOException when can't receive a web page
     */
    public List<Vacancy> getVacancies(String region, String query) throws IOException {
        List<Vacancy> vacancies = new ArrayList<>();
        switch (region.toUpperCase()) {
            case "UA" -> {
                for (String source : uaURLs) {
                    tuneUpStrategy(source);
                    vacancies.addAll(strategy.getVacancies(query));
                }
                return vacancies;
            }
            case "RU" -> {
                for (String source : ruURLs) {
                    tuneUpStrategy(source);
                    vacancies.addAll(strategy.getVacancies(query));
                }
                return vacancies;
            }
            default -> throw new IllegalArgumentException("Region is incorrect");
        }
    }
}
