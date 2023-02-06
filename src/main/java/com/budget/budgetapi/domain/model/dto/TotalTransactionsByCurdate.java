package com.budget.budgetapi.domain.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class TotalTransactionsByCurdate {
    private Date currentDate;
    private Long totalTransactions;
    private BigDecimal totalValue;
}
