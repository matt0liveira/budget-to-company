package com.budget.budgetapi.api.openapi.controlller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.budget.budgetapi.api.exceptionhandler.ProblemApi;
import com.budget.budgetapi.api.model.TransactionModel;
import com.budget.budgetapi.api.model.input.TransactionInputModel;
import com.budget.budgetapi.domain.filter.TransactionFilter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Transactions")
public interface TransactionControllerOpenApi {

	@Operation(summary = "Search transactions")
	@Parameters({
			@Parameter(name = "type", required = false, example = "EXPENSE"),
			@Parameter(name = "userId", required = false, example = "1"),
			@Parameter(name = "categoryId", required = false, example = "2"),
			@Parameter(name = "dateTransactionInitial", required = false, example = "2000-01-01"),
			@Parameter(name = "dateTransactionFinal", required = false, example = "2000-02-02")
	})
	ResponseEntity<List<TransactionModel>> toSearch(@Parameter(hidden = true) TransactionFilter filter);

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Success request"),
			@ApiResponse(responseCode = "404", description = "Transaction not found", content = @Content(schema = @Schema(implementation = ProblemApi.class)))
	})
	@Operation(summary = "Find a transaction by CODE")
	TransactionModel toFind(String transactionCode);

	@Operation(summary = "Add a new transaction")
	ResponseEntity<TransactionModel> toAdd(TransactionInputModel transactionInput);

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Success request"),
			@ApiResponse(responseCode = "404", description = "Transaction not found", content = @Content(schema = @Schema(implementation = ProblemApi.class)))
	})
	@Operation(summary = "Partially update data transaction by CODE")
	TransactionModel toPartialUpdate(String transactionCode,
			@RequestBody(content = @Content(schema = @Schema(implementation = TransactionInputModel.class))) Map<String, Object> fields,
			HttpServletRequest request);

	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "No content"),
			@ApiResponse(responseCode = "404", description = "Transaction not found", content = @Content(schema = @Schema(implementation = ProblemApi.class)))
	})
	@Operation(summary = "Remove a transaction by CODE")
	ResponseEntity<Void> remove(String transactionCode);
}
