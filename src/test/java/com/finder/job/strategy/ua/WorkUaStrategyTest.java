package com.finder.job.strategy.ua;

import com.finder.job.models.Vacancy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorkUaStrategyTest {
    static WorkUaStrategy workUaStrategy;

    @BeforeAll
    static void beforeAll() {
        workUaStrategy=  new WorkUaStrategy();
    }

//    @BeforeEach
//    void setUp() {
//
//    }

    @Test
    public void testGetWorkUaVacanciesWithMainstreamQuery() throws IOException {
        List<Vacancy> vacancyList = workUaStrategy.getVacancies("javascript");
        assertTrue(vacancyList.size() >= 14);

    }

    @Test
    public void testGetWorkUaVacanciesWithOnlyOnePage() throws IOException {
        List<Vacancy> vacancyList = workUaStrategy.getVacancies("java angular");
        assertTrue(vacancyList.size() < 14);
    }

    @Test
    public void testGetWorkUaVacanciesWithWrongQuery() throws IOException {
        List<Vacancy> vacancyList = workUaStrategy.getVacancies("qwerty");
        assertEquals(0, vacancyList.size());
    }

    @Test
    public void speedTest() throws IOException {
        long before = System.currentTimeMillis();
        workUaStrategy.getVacancies("javascript junior");
        long after = System.currentTimeMillis();
        System.out.println("Method has been finished in :" + (after-before) + " ms");
    }

}