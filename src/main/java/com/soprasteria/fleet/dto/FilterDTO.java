package com.soprasteria.fleet.dto;

import com.soprasteria.fleet.dto.dtoUtils.DTOEntity;

public class FilterDTO implements DTOEntity {
    private String filter;
    private String option;

    public String getFilter() {
        return filter;
    }

    public String getOption() {
        return option;
    }

    public FilterDTO(String filter, String option) {
        this.filter = filter;
        this.option = option;
    }

    public FilterDTO() {
    }
}
