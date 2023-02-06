package com.budget.budgetapi.domain.model.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class TotalTransactionsByWeekCurrent {

    private String dayName;
    private Integer dayNumber;
    private Long totalTransactions;
    private BigDecimal totalValue;
}
