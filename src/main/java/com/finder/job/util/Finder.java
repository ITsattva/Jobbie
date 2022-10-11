package com.finder.job.util;

import com.finder.job.models.Vacancy;
import com.finder.job.strategy.Strategy;
import com.finder.job.strategy.ru.HeadHunterStrategy;
import com.finder.job.strategy.ua.*;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
@NoArgsConstructor
@Log4j2
public class Finder {
    private final List<String> uaURLs = new ArrayList<>(Arrays.asList(
            "Djinni",
            "DOU",
            "WorkUa",
            "RabotaUa",
            "LinkedIn"));
    private final List<String> ruURLs = new ArrayList<>(Arrays.asList(
            "HeadHunter"
    ));

    private Strategy strategy;

    private void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void tuneUpStrategy(String source) {
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

    public List<Vacancy> getVacancies(String region, String query) throws IOException {
        List<Vacancy> vacancies = new ArrayList<>();

        switch (region) {
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
