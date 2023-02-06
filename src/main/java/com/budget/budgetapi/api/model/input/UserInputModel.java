package com.budget.budgetapi.api.model.input;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserInputModel {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotNull
    @PositiveOrZero
    private BigDecimal balance;

}