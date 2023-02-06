package com.budget.budgetapi.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryModelWithUser {

    private Long id;
    private String description;
    private String color;
    private Boolean inactive;
    private UserModel user;
}
