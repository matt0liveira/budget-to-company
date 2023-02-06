package com.budget.budgetapi.api.openapi.controlller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.budget.budgetapi.api.exceptionhandler.ProblemApi;
import com.budget.budgetapi.api.model.CategoryModelWithUser;
import com.budget.budgetapi.api.model.input.CategoryInputModel;
import com.budget.budgetapi.domain.filter.CategoryFilter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Categories")
public interface CategoryControllerOpenApi {

	@Operation(summary = "List all categories")
	@Parameters({
			@Parameter(name = "userId", required = false, example = "1"),
			@Parameter(name = "onlyActive", required = false, example = "true"),
			@Parameter(name = "description", required = false, example = "Finance")
	})
	ResponseEntity<List<CategoryModelWithUser>> toList(@Parameter(hidden = true) CategoryFilter filter);

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Success request"),
			@ApiResponse(responseCode = "404", description = "Category not found", content = @Content(schema = @Schema(implementation = ProblemApi.class)))
	})
	@Operation(summary = "Find a category by ID")
	CategoryModelWithUser toFind(Long categoryId);

	@Operation(summary = "Add a new category")
	ResponseEntity<CategoryModelWithUser> toAdd(CategoryInputModel categoryInput);

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Success request"),
			@ApiResponse(responseCode = "404", description = "Category not found", content = @Content(schema = @Schema(implementation = ProblemApi.class)))
	})
	@Operation(summary = "Update data category by ID")
	CategoryModelWithUser toUpdate(Long categoryId,
			CategoryInputModel categoryInput);

	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "No content"),
			@ApiResponse(responseCode = "404", description = "Category not found", content = @Content(schema = @Schema(implementation = ProblemApi.class)))
	})
	@Operation(summary = "Remove a category by ID")
	ResponseEntity<Void> remove(Long categoryId);

	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "No content"),
			@ApiResponse(responseCode = "404", description = "Category not found", content = @Content(schema = @Schema(implementation = ProblemApi.class)))
	})
	@Operation(summary = "Activate a category by ID")
	ResponseEntity<Void> toActivate(Long categoryId);

	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "No content"),
			@ApiResponse(responseCode = "404", description = "Category not found", content = @Content(schema = @Schema(implementation = ProblemApi.class)))
	})
	@Operation(summary = "Inactivate a category by ID")
	ResponseEntity<Void> toInactivate(Long categoryId);
}
