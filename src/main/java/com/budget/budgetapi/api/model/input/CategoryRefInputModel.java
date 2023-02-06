package com.budget.budgetapi.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryRefInputModel {

    @NotNull
    private Long id;
}
