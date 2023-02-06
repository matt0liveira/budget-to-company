package com.budget.budgetapi.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryModel {

    private Long id;
    private String description;
    private String color;

    @Schema(defaultValue = "false")
    private Boolean inactive;
}
