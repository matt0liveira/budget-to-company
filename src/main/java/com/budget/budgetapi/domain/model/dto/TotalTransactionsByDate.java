package com.budget.budgetapi.domain.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class TotalTransactionsByDate {

    private Date date;
    private Long totalTransactions;
    private BigDecimal totalValue;
}
