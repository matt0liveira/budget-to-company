package com.budget.budgetapi.domain.model.enums;

import lombok.Getter;

@Getter
public enum TypePaymentMethod {

    DEBIT_CARD("Debit_Card"),
    CREDIT_CARD("Credit_Card"),
    MONEY("Money"),
    PIX("Pix");

    private String description;

    TypePaymentMethod(String description) {
        this.description = description;
    }

}
