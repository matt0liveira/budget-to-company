package com.budget.budgetapi.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.budget.budgetapi.api.openapi.controlller.AnalyticsControllerOpenApi;
import com.budget.budgetapi.core.security.CheckSecurity;
import com.budget.budgetapi.domain.filter.TotalTransactionsWithoutDateFilter;
import com.budget.budgetapi.domain.filter.TransactionFilter;
import com.budget.budgetapi.domain.model.dto.TotalTransactions;
import com.budget.budgetapi.domain.model.dto.TotalTransactionsByCurdate;
import com.budget.budgetapi.domain.model.dto.TotalTransactionsByDate;
import com.budget.budgetapi.domain.model.dto.TotalTransactionsByMonth;
import com.budget.budgetapi.domain.model.dto.TotalTransactionsByWeekCurrent;
import com.budget.budgetapi.domain.model.dto.TotalTransactionsLastFourYears;
import com.budget.budgetapi.domain.service.TransactionsAnalyticsQueryService;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController implements AnalyticsControllerOpenApi {

    @Autowired
    private TransactionsAnalyticsQueryService transactionsAnalyticsService;

    @CheckSecurity.Analytics.CanConsult
    @GetMapping("/total-transactions-by-date")
    public ResponseEntity<List<TotalTransactionsByDate>> queryTotalTransactionsByDate(
            TransactionFilter filter) {
        return ResponseEntity.ok().body(transactionsAnalyticsService.queryTotalTransactionsByDate(filter));
    }

    @CheckSecurity.Analytics.CanConsult
    @GetMapping("/total-transactions")
    public ResponseEntity<List<TotalTransactions>> queryTotalTransactions(TransactionFilter filter) {
        return ResponseEntity.ok().body(transactionsAnalyticsService.queryTotalTransactions(filter));
    }

    @CheckSecurity.Analytics.CanConsult
    @GetMapping("/total-transactions-last-four-years")
    public ResponseEntity<List<TotalTransactionsLastFourYears>> queryTotalTransactionsLastFourYears(
            TotalTransactionsWithoutDateFilter filter) {
        return ResponseEntity.ok().body(transactionsAnalyticsService.queryTotalTransactionsLastFourYears(filter));
    }

    @CheckSecurity.Analytics.CanConsult
    @GetMapping("/total-transactions-by-current-date")
    public ResponseEntity<List<TotalTransactionsByCurdate>> queryTotalTransactionsByCurdate(
            TotalTransactionsWithoutDateFilter filter) {
        return ResponseEntity.ok().body(transactionsAnalyticsService.queryTotalTransactionsByCurdate(filter));
    }

    @CheckSecurity.Analytics.CanConsult
    @GetMapping("/total-transactions-by-month")
    public ResponseEntity<List<TotalTransactionsByMonth>> queryTotalTransactionsByMonths(
            TotalTransactionsWithoutDateFilter filter) {
        return ResponseEntity.ok().body(transactionsAnalyticsService.queryTotalTransactionsByMonths(filter));
    }

    @CheckSecurity.Analytics.CanConsult
    @GetMapping("/total-transactions-by-week-current")
    public ResponseEntity<List<TotalTransactionsByWeekCurrent>> queryTotalTransactionsByWeekCurrent(
            TotalTransactionsWithoutDateFilter filter) {
        return ResponseEntity.ok().body(transactionsAnalyticsService.queryTotalTransactionsByWeekCurrent(filter));
    }

}