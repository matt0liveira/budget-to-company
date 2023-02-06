package com.budget.budgetapi.domain.model.enums;

import lombok.Getter;

@Getter
public enum TypeTransaction {
    EXPENSE("Expense"),
    INCOME("Income");

    private String description;

    private TypeTransaction(String description) {
        this.description = description;
    }

}