package com.budget.budgetapi.domain.model.enums;

import lombok.Getter;

@Getter
public enum TypeSupplierCustomer {

    SUPPLIER("Supplier"),
    CUSTOMER("Customer");

    private String description;

    TypeSupplierCustomer(String description) {
        this.description = description;
    }

}
