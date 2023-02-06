package com.budget.budgetapi.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangePasswordInputModel {

    @NotBlank
    private String passwordCurrent;

    @NotBlank
    private String newPassword;
}
