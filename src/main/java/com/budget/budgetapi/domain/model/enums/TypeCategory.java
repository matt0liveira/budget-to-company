package com.budget.budgetapi.domain.model.enums;

import lombok.Getter;

@Getter
public enum TypeCategory {
    EXPENSE("Expense"),
    INCOME("Income");

    private String description;

    private TypeCategory(String description) {
        this.description = description;
    }

}