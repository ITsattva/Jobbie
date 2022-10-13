package com.finder.job.models.vacancy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Vacancy {
    private String title;
    private String company;
    private String town;
    private String description;
    private String salary;
    private String link;
    private String date;

    @Override
    public String toString(){
        return "\nTitle: " + title +
                "\nCompany: " + company +
                "\nTown: " + town +
                "\nSalary: " + salary +
                "\nDescription: " + description +
                "\nDate: " + date +
                "\nLink: " + link + "\n";
    }
}
