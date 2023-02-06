package com.budget.budgetapi.domain.model;

import lombok.Data;

@Data
public class Address {

    private String city;

    private String uf;

    private String cep;

    private String place;

    private Integer number;

    private String complement;

    private String district;
}
