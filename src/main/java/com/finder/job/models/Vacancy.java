package com.finder.job.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vacancy {
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
                "\nLink: " + link;
    }
}
