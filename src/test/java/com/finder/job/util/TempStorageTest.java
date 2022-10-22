package com.finder.job.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Yurii Piatkin
 */
class TempStorageTest {
    List<String> testData;
    TempStorage<String> tempStorage;

    @BeforeEach
    void setUp() {
        tempStorage = new TempStorage<>();
        testData = Arrays.asList("First", "Second", "Third", "Fourth", "Fifth", "Sixth");
    }

    @Test
    void checkDataForRepetitionTrueTest() {
        List<String> storeDataTest = Arrays.asList("First", "Second", "Third", "Fourth", "Fifth", "Sixth");
        tempStorage.storeData(testData);
        Assertions.assertTrue(tempStorage.checkDataForRepetition(storeDataTest));
    }

    @Test
    void checkDataForRepetitionFalseTest() {
        List<String> storeDataTest = Arrays.asList("Sixth", "Second", "Third", "Fourth", "Fifth");
        tempStorage.storeData(testData);
        Assertions.assertFalse(tempStorage.checkDataForRepetition(storeDataTest));
    }

    @Test
    void storeDataTest() {
        List<String> storeDataTest = Arrays.asList("Sixth", "Second", "Third", "Fourth", "Fifth");
        tempStorage.storeData(storeDataTest);

        Assertions.assertEquals(storeDataTest.get(0), tempStorage.getData().get(0));
    }

    @Test
    void getDataTest() {
        tempStorage.storeData(testData);

        Assertions.assertEquals(tempStorage.getData(), testData);
    }

    @Test
    void dontAddIfThereIsRepetition() {
        //todo stub
    }
}