package com.finder.job.util;

import com.finder.job.models.vacancy.Vacancy;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class FinderTest {
    Finder finder = new Finder();

    @Test
    void getVacancies() throws IOException {
        List<Vacancy> vacancies = finder.getVacancies("UA", "java");
        System.out.println(vacancies);
    }

    @Test
    public void speedTest() throws IOException {
        long before = System.currentTimeMillis();
        List<Vacancy> vacancies = finder.getVacancies("UA","javascript junior");
        long after = System.currentTimeMillis();
        System.out.println("Finder speed test has been completed!");
        System.out.println(vacancies.size() + " vacancies has been found in: " + (after-before) + " ms");
        System.out.printf("Average time for one vacancy is: %.0f ms", ((double)(after-before)/vacancies.size()));
    }
}