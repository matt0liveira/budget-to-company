package com.budget.budgetapi.domain.model.enums;

import lombok.Getter;

@Getter
public enum TypeAccount {

    CHECKING("Checking"),
    INTERNAL("Internal"),
    SAVING("Saving"),
    MONEY("Money");

    private String description;

    TypeAccount(String description) {
        this.description = description;
    }

}
