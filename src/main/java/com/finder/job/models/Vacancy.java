package com.finder.job.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Vacancy {
    private String title;
    private String description;
    private String salary;
}
