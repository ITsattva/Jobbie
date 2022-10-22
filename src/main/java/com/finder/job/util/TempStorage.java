package com.finder.job.util;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Class for handling queries to site.
 * We don't need to make query everytime when we use pagination,
 * so this class stores some temporary data.
 * @param <T> type of data
 */

@Component
public class TempStorage<T> {
    private List<T> data;

    public boolean checkDataForRepetition(List<T> data){
        return this.data.get(0).equals(data.get(0));
    }

    public void storeData(List<T> data){
        this.data = data;
    }

    public List<T> getData(){
        return data;
    }
}
