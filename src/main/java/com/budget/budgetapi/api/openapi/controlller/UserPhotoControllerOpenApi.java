package com.budget.budgetapi.api.openapi.controlller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import com.budget.budgetapi.api.exceptionhandler.ProblemApi;
import com.budget.budgetapi.api.model.PhotoUserModel;
import com.budget.budgetapi.api.model.input.PhotoUserInputModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Users")
public interface UserPhotoControllerOpenApi {

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Success request"),

			@ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ProblemApi.class)))
	})
	@Operation(summary = "Update user photo by USER ID")
	ResponseEntity<PhotoUserModel> toUpdate(Long userId,
			PhotoUserInputModel photoUserInput, MultipartFile file) throws IOException;

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Success request"),

			@ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ProblemApi.class)))
	})
	@Operation(summary = "Find user photo by USER ID")
	ResponseEntity<?> toFind(Long userId,
			@Parameter(hidden = true, required = false) String acceptHeader) throws HttpMediaTypeNotAcceptableException;

	@Operation(summary = "Remove user photo by USER ID")
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "No content"),

			@ApiResponse(responseCode = "404", description = "User not found")
	})
	ResponseEntity<Void> remove(Long userId);
}
