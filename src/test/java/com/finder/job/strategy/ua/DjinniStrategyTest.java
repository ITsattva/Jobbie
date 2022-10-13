package com.finder.job.strategy.ua;

import com.finder.job.models.vacancy.Vacancy;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DjinniStrategyTest {
    DjinniStrategy djinniStrategy = new DjinniStrategy();


    @Test
    public void showVacancies() throws IOException {
        List<Vacancy> vacancyList = djinniStrategy.getVacancies("swift");
        System.out.println(vacancyList);
    }

    @Test
    public void testGetDjinniVacanciesWithMainstreamQuery() throws IOException {
        List<Vacancy> vacancyList = djinniStrategy.getVacancies("javascript");
        assertTrue(vacancyList.size() >= 14);
    }

    @Test
    public void testGetDjinniVacanciesWithOnlyOnePage() throws IOException {
        List<Vacancy> vacancyList = djinniStrategy.getVacancies("java angular");
        assertTrue(vacancyList.size() < 14);
    }

    @Test
    public void testGetDjinniVacanciesWithWrongQuery() throws IOException {
        List<Vacancy> vacancyList = djinniStrategy.getVacancies("qwerty");
        assertEquals(0, vacancyList.size());
    }

    @Test
    public void speedDjinniTest() throws IOException {
        long before = System.currentTimeMillis();
        List<Vacancy> vacancies = djinniStrategy.getVacancies("swift");
        long after = System.currentTimeMillis();
        System.out.println("Djinni speed test has been completed!");
        System.out.println(vacancies.size() + " vacancies has been found in: " + (after-before) + " ms");
        System.out.printf("Average time for one vacancy is: %.0f ms", ((double)(after-before)/vacancies.size()));
    }
}