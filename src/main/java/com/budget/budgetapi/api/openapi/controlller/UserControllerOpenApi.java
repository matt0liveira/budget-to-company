package com.budget.budgetapi.api.openapi.controlller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.budget.budgetapi.api.exceptionhandler.ProblemApi;
import com.budget.budgetapi.api.model.UserModel;
import com.budget.budgetapi.api.model.input.ChangePasswordInputModel;
import com.budget.budgetapi.api.model.input.UserInputModel;
import com.budget.budgetapi.api.model.input.UserWithPasswordInputModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Users")
public interface UserControllerOpenApi {

	@Operation(summary = "List all users")
	ResponseEntity<List<UserModel>> toList();

	@Operation(summary = "Find an user by ID")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Success request"),

			@ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ProblemApi.class)))
	})
	ResponseEntity<UserModel> toFind(Long userId);

	@Operation(summary = "Add an user")
	@ApiResponse(responseCode = "201", description = "Created")
	ResponseEntity<UserModel> toAdd(@Valid UserWithPasswordInputModel userInput);

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Success request"),

			@ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ProblemApi.class)))
	})
	@Operation(summary = "Update user data by ID")
	ResponseEntity<UserModel> toUpdate(Long userId, @Valid UserInputModel userInput);

	@ApiResponses({
			@ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ProblemApi.class))),

			@ApiResponse(responseCode = "204", description = "No content")
	})
	@Operation(summary = "Remove an user by ID")
	ResponseEntity<Void> remove(Long userId);

	@ApiResponses({
			@ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ProblemApi.class))),

			@ApiResponse(responseCode = "204", description = "No content")
	})
	@Operation(summary = "Change user password by ID")
	ResponseEntity<Void> toChangePassword(Long userId, ChangePasswordInputModel changePasswordInput);
}
