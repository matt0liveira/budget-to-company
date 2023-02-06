package com.budget.budgetapi.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProfileInputModel {

    @NotBlank
    private String name;
}
