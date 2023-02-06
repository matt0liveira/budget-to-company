package com.budget.budgetapi.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PermissionInputModel {

    @NotBlank
    private String name;

    @NotBlank
    private String description;
}
