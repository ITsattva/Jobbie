package com.finder.job.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Vacancy {
    private String title;
    private String description;
    private String salary;
    private String site;
    private LocalDateTime dateTime;
}
