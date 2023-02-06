package com.budget.budgetapi.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserWithPasswordInputModel extends UserInputModel {

    @NotBlank
    private String password;
}
