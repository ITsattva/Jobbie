package com.finder.job.models.vacancy;

import lombok.NoArgsConstructor;

/**
 * implementation of Vacancy for DOU site
 */
@NoArgsConstructor
public class DouVacancy extends Vacancy {
    public DouVacancy(String title, String company, String town, String description, String salary, String link, String date) {
        super(title, company, town, description, salary, link, date);
    }
}
