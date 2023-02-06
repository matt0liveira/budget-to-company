package com.budget.budgetapi.domain.service;

import java.util.List;

import com.budget.budgetapi.domain.filter.TotalTransactionsWithoutDateFilter;
import com.budget.budgetapi.domain.filter.TransactionFilter;
import com.budget.budgetapi.domain.model.dto.TotalTransactions;
import com.budget.budgetapi.domain.model.dto.TotalTransactionsByCurdate;
import com.budget.budgetapi.domain.model.dto.TotalTransactionsByDate;
import com.budget.budgetapi.domain.model.dto.TotalTransactionsByMonth;
import com.budget.budgetapi.domain.model.dto.TotalTransactionsByWeekCurrent;
import com.budget.budgetapi.domain.model.dto.TotalTransactionsLastFourYears;

public interface TransactionsAnalyticsQueryService {
    List<TotalTransactionsByDate> queryTotalTransactionsByDate(TransactionFilter filter);

    List<TotalTransactions> queryTotalTransactions(TransactionFilter filter);

    List<TotalTransactionsLastFourYears> queryTotalTransactionsLastFourYears(
            TotalTransactionsWithoutDateFilter filter);

    List<TotalTransactionsByCurdate> queryTotalTransactionsByCurdate(TotalTransactionsWithoutDateFilter filter);

    List<TotalTransactionsByMonth> queryTotalTransactionsByMonths(TotalTransactionsWithoutDateFilter filter);

    List<TotalTransactionsByWeekCurrent> queryTotalTransactionsByWeekCurrent(TotalTransactionsWithoutDateFilter filter);
}
