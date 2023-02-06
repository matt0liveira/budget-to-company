package com.budget.budgetapi.api.openapi.controlller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.budget.budgetapi.api.exceptionhandler.ProblemApi;
import com.budget.budgetapi.api.model.ProfileModel;
import com.budget.budgetapi.api.model.input.ProfileInputModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Profiles")
public interface ProfileControllerOpenApi {

	@Operation(summary = "List all profiles")
	ResponseEntity<List<ProfileModel>> toList();

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Success request"),

			@ApiResponse(responseCode = "404", description = "Profile not found", content = @Content(schema = @Schema(implementation = ProblemApi.class)))
	})
	@Operation(summary = "Find a profile by ID")
	ResponseEntity<ProfileModel> toFind(Long profileId);

	@ApiResponse(responseCode = "201", description = "Created")
	@Operation(summary = "Add a new profile")
	ResponseEntity<ProfileModel> toAdd(ProfileInputModel profileInput);

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Success request"),

			@ApiResponse(responseCode = "404", description = "Profile not found", content = @Content(schema = @Schema(implementation = ProblemApi.class)))
	})
	@Operation(summary = "Update profile data by ID")
	ResponseEntity<ProfileModel> toUpdate(Long profileId,
			ProfileInputModel profileInput);

	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "No content"),

			@ApiResponse(responseCode = "404", description = "Profile not found")
	})
	@Operation(summary = "Remove a profile by ID")
	ResponseEntity<Void> remove(Long profileId);
}
