package com.budget.budgetapi.api.openapi.controlller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.budget.budgetapi.domain.filter.TotalTransactionsWithoutDateFilter;
import com.budget.budgetapi.domain.filter.TransactionFilter;
import com.budget.budgetapi.domain.model.dto.TotalTransactions;
import com.budget.budgetapi.domain.model.dto.TotalTransactionsByCurdate;
import com.budget.budgetapi.domain.model.dto.TotalTransactionsByDate;
import com.budget.budgetapi.domain.model.dto.TotalTransactionsByMonth;
import com.budget.budgetapi.domain.model.dto.TotalTransactionsByWeekCurrent;
import com.budget.budgetapi.domain.model.dto.TotalTransactionsLastFourYears;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Analytics")
public interface AnalyticsControllerOpenApi {

	@Parameters({
			@Parameter(name = "type", example = "EXPENSE,INCOME"),
			@Parameter(name = "userId", example = "1"),
			@Parameter(name = "categoryId", example = "2"),
			@Parameter(name = "dateTransactionInitial", example = "2000-01-01"),
			@Parameter(name = "dateTransactionFinal", example = "2000-02-02")
	})
	@Operation(summary = "Total transactions report grouped by date")
	ResponseEntity<List<TotalTransactionsByDate>> queryTotalTransactionsByDate(
			@Parameter(hidden = true) TransactionFilter filter);

	@Parameters({
			@Parameter(name = "type", example = "EXPENSE,INCOME"),
			@Parameter(name = "userId", example = "1"),
			@Parameter(name = "categoryId", example = "2"),
			@Parameter(name = "dateTransactionInitial", example = "2000-01-01"),
			@Parameter(name = "dateTransactionFinal", example = "2000-02-02")
	})
	@Operation(summary = "Total transactions report")
	ResponseEntity<List<TotalTransactions>> queryTotalTransactions(@Parameter(hidden = true) TransactionFilter filter);

	@Parameters({
			@Parameter(name = "type", example = "EXPENSE,INCOME"),
			@Parameter(name = "userId", example = "1"),
			@Parameter(name = "categoryId", example = "2")
	})
	@Operation(summary = "Report of total transactions over the last four years")
	ResponseEntity<List<TotalTransactionsLastFourYears>> queryTotalTransactionsLastFourYears(
			@Parameter(hidden = true) TotalTransactionsWithoutDateFilter filter);

	@Parameters({
			@Parameter(name = "type", example = "EXPENSE,INCOME"),
			@Parameter(name = "userId", example = "1"),
			@Parameter(name = "categoryId", example = "2")
	})
	@Operation(summary = "Total transactions report grouped by current date")
	ResponseEntity<List<TotalTransactionsByCurdate>> queryTotalTransactionsByCurdate(
			@Parameter(hidden = true) TotalTransactionsWithoutDateFilter filter);

	@Parameters({
			@Parameter(name = "type", example = "EXPENSE,INCOME"),
			@Parameter(name = "userId", example = "1"),
			@Parameter(name = "categoryId", example = "2")
	})
	@Operation(summary = "Total transactions report grouped by months of year")
	ResponseEntity<List<TotalTransactionsByMonth>> queryTotalTransactionsByMonths(
			@Parameter(hidden = true) TotalTransactionsWithoutDateFilter filter);

	@Parameters({
			@Parameter(name = "type", example = "EXPENSE,INCOME"),
			@Parameter(name = "userId", example = "1"),
			@Parameter(name = "categoryId", example = "2")
	})
	@Operation(summary = "Total transactions report grouped by current week")
	ResponseEntity<List<TotalTransactionsByWeekCurrent>> queryTotalTransactionsByWeekCurrent(
			@Parameter(hidden = true) TotalTransactionsWithoutDateFilter filter);
}
