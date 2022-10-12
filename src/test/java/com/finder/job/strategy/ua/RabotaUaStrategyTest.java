package com.finder.job.strategy.ua;

import com.finder.job.models.Vacancy;
import com.finder.job.pojo.RabotaUaPOJO;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RabotaUaStrategyTest {
    RabotaUaStrategy rabotaUaStrategy = new RabotaUaStrategy();

    @Test
    public void getResponseFromAPI() throws IOException {
        List<Vacancy> vacancyList = rabotaUaStrategy.getVacancies("Java");

        System.out.println("Vacancies size: " + vacancyList.size());
        System.out.println(vacancyList);
    }

    @Test
    public void testGetWorkUaVacanciesWithMainstreamQuery() throws IOException {
        List<Vacancy> vacancyList = rabotaUaStrategy.getVacancies("javascript");
        assertTrue(vacancyList.size() >= 14);

    }

    @Test
    public void testGetWorkUaVacanciesWithOnlyOnePage() throws IOException {
        List<Vacancy> vacancyList = rabotaUaStrategy.getVacancies("dart");
        assertTrue(vacancyList.size() < 14);
    }

    @Test
    public void testGetWorkUaVacanciesWithWrongQuery() throws IOException {
        List<Vacancy> vacancyList = rabotaUaStrategy.getVacancies("qwerty");
        assertEquals(0, vacancyList.size());
    }

    @Test
    public void speedTest() throws IOException {
        long before = System.currentTimeMillis();
        rabotaUaStrategy.getVacancies("javascript junior");
        long after = System.currentTimeMillis();
        System.out.println("Method has been finished in :" + (after-before) + " ms");
    }
}