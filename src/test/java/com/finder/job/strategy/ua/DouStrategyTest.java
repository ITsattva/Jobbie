package com.finder.job.strategy.ua;

import com.finder.job.models.vacancy.Vacancy;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DouStrategyTest {
    DouStrategy douStrategy = new DouStrategy();


    @Test
    public void showVacancies() throws IOException {
        List<Vacancy> vacancyList = douStrategy.getVacancies("java");
        System.out.println(vacancyList);
    }

    @Test
    public void testGetDouVacanciesWithMainstreamQuery() throws IOException {
        List<Vacancy> vacancyList = douStrategy.getVacancies("javascript");
        assertTrue(vacancyList.size() >= 14);
    }

    @Test
    public void testGetDouVacanciesWithOnlyOnePage() throws IOException {
        List<Vacancy> vacancyList = douStrategy.getVacancies("java angular");
        assertTrue(vacancyList.size() < 14);
    }

    @Test
    public void testGetDouVacanciesWithWrongQuery() throws IOException {
        List<Vacancy> vacancyList = douStrategy.getVacancies("qwerty");
        assertEquals(0, vacancyList.size());
    }

    @Test
    public void speedDouTest() throws IOException {
        long before = System.currentTimeMillis();
        List<Vacancy> vacancies = douStrategy.getVacancies("swift");
        long after = System.currentTimeMillis();
        System.out.println("DOU speed test has been completed!");
        System.out.println(vacancies.size() + " vacancies has been found in: " + (after-before) + " ms");
        System.out.printf("Average time for one vacancy is: %.0f ms", ((double)(after-before)/vacancies.size()));
    }
}