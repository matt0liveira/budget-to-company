package com.budget.budgetapi.api.model;

import java.math.BigDecimal;
import java.sql.Date;

import com.budget.budgetapi.domain.model.enums.TypeTransaction;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionModel {

    private String code;
    private BigDecimal value;
    private String description;
    private TypeTransaction typeTransaction;

    @Schema(format = "date", type = "string")
    private Date date;
    private UserModel user;
    private CategoryModel category;
}