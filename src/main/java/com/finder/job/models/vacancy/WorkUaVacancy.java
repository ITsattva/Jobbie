package com.finder.job.models.vacancy;

import lombok.NoArgsConstructor;

/**
 * implementation of Vacancy for Work.ua site
 */
@NoArgsConstructor
public class WorkUaVacancy extends Vacancy {
    public WorkUaVacancy(String title, String company, String town, String description, String salary, String link, String date) {
        super(title, company, town, description, salary, link, date);
    }
}
