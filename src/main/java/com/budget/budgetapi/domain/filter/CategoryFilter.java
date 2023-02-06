package com.budget.budgetapi.domain.filter;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryFilter {

    private Long userId;

    private Boolean onlyActive;

    private String description;

}
