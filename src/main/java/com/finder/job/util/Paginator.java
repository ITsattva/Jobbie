package com.finder.job.util;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * Class for handling pagination operations
 * @param <T> type of data for pagination
 */

@Component
public class Paginator<T> {

    /**
     * Returns a page from list
     * @param page number of page to be returned
     * @param data data for splitting into pages
     * @return part of data from parameter
     */
    public List<T> getPage(int page, List<T> data){
        PagedListHolder<T> result = new PagedListHolder<>(data);
        int pageSize = 20;
        result.setPageSize(pageSize);
        result.setPage(page);

        return result.getPageList();
    }

    /**
     * Returns total count of pages
     * @param data list of data
     * @return total count of pages
     */
    public int getPagesCount(List<T> data){
        int pagesCount = data.size() / 20;
        return data.size() % 20 == 0 ? pagesCount : pagesCount + 1;
    }
}
