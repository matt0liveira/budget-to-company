package com.budget.budgetapi.domain.filter;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.budget.budgetapi.domain.model.enums.TypeTransaction;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionFilter {

    private TypeTransaction type;

    private Long userId;

    private Long categoryId;

    @DateTimeFormat(iso = ISO.DATE)
    private Date dateTransactionInitial;

    @DateTimeFormat(iso = ISO.DATE)
    private Date dateTransactionFinal;
}
