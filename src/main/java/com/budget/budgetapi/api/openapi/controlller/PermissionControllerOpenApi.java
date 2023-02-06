package com.budget.budgetapi.api.openapi.controlller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.budget.budgetapi.api.exceptionhandler.ProblemApi;
import com.budget.budgetapi.api.model.PermissionModel;
import com.budget.budgetapi.api.model.input.PermissionInputModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Permissions")
public interface PermissionControllerOpenApi {

	@Operation(summary = "List all permissions")
	ResponseEntity<List<PermissionModel>> toList();

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Success request"),

			@ApiResponse(responseCode = "404", description = "Permission not found", content = @Content(schema = @Schema(implementation = ProblemApi.class)))
	})
	@Operation(summary = "Find a permission by ID")
	ResponseEntity<PermissionModel> toFind(Long permissionId);

	@Operation(summary = "Add a new permission")
	@ApiResponse(responseCode = "201", description = "Created")
	ResponseEntity<PermissionModel> toAdd(PermissionInputModel permissionInput);

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Success request"),

			@ApiResponse(responseCode = "404", description = "Permission not found", content = @Content(schema = @Schema(implementation = ProblemApi.class)))
	})
	@Operation(summary = "Update data permission by ID")
	ResponseEntity<PermissionModel> toUpdate(Long permissionId,
			PermissionInputModel permissionInput);

	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "No content"),

			@ApiResponse(responseCode = "404", description = "Permission not found", content = @Content(schema = @Schema(implementation = ProblemApi.class)))
	})
	@Operation(summary = "Remove a permission by ID")
	ResponseEntity<Void> remove(Long permissionId);
}
