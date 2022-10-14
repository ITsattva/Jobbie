package com.finder.job.models.vacancy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * implementation of Vacancy for Djinni site
 */
@NoArgsConstructor
public class DjinniVacancy extends Vacancy {
    public DjinniVacancy(String title, String company, String town, String description, String salary, String link, String date) {
        super(title, company, town, description, salary, link, date);
    }
}
