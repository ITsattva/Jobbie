package com.finder.job.pojo;

import lombok.Data;

import java.util.List;

/**
 * POJO entity for parsing from JSONobject
 */
@Data
public class RabotaUaPOJO {
    private List<VacancyShortDto> documents;

    @Data
    public static class VacancyShortDto {
        private Integer id;
        private String name;
        private String companyName;
        private String cityName;
        private String shortDescription;
        private Integer salary;
        private String dateTxt;
    }

    @Data
    static class VacancyBadgeDto {
        Integer id;
        String name;
    }
}
