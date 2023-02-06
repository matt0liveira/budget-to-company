package com.budget.budgetapi.domain.model.enums;

import lombok.Getter;

@Getter
public enum StatusTransaction {

    OPEN("Open"),
    PAID("Paid"),
    INSTALLMENT("Installment");

    private String description;

    StatusTransaction(String description) {
        this.description = description;
    }

}
