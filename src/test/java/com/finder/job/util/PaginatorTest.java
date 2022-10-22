package com.finder.job.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Yurii Piatkin
 */
class PaginatorTest {
    Paginator<String> paginator;
    List<String> testList;

    @BeforeEach
    void setUp() {
        paginator = new Paginator<>();
        testList = new ArrayList<>();
        for (int i = 0; i < 123; i++) {
            testList.add(i + " string");
        }
    }

    @Test
    void getPage() {
        Assertions.assertEquals(paginator.getPage(6, testList), Arrays.asList("120 string", "121 string", "122 string"));
    }

    @Test
    void getPagesCount() {
        Assertions.assertEquals(paginator.getPagesCount(testList), 7);
    }

    @Test
    void getPagesHugeCount() {
        System.out.println(paginator.getPagesCount(testList));
        Assertions.assertEquals(paginator.getPagesCount(testList), 7);
    }
}