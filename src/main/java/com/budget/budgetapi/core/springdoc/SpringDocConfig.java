package com.budget.budgetapi.core.springdoc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import com.budget.budgetapi.api.exceptionhandler.ProblemApi;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
@SecurityScheme(name = "security_auth", type = SecuritySchemeType.OAUTH2, flows = @OAuthFlows(authorizationCode = @OAuthFlow(authorizationUrl = "${springdoc.oAuthFlow.authorizationUrl}", tokenUrl = "${springdoc.oAuthFlow.tokenUrl}", scopes = {
		@OAuthScope(name = "READ", description = "read scope"),
		@OAuthScope(name = "WRITE", description = "write scope")
})))
public class SpringDocConfig {

	@Bean
	OpenAPI openAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Budget REST API Documentation")
						.description("API Project made with Spring")
						.version("v1.0")
						.license(new License().name("Apache 2.0").url("http://springdoc.org")))
				.tags(Arrays.asList(
						new Tag().name("Users").description("Manage users"),
						new Tag().name("Profiles").description("Manage profiles"),
						new Tag().name("Permissions").description("Manage permissions"),
						new Tag().name("Categories").description("Manage categories"),
						new Tag().name("Transactions").description("Manage transactions"),
						new Tag().name("Analytics").description("Manage analysis reports")))
				.components(new Components()
						.schemas(generateSchemas())
						.responses(generateResponses()));
	}

	@Bean
	OpenApiCustomiser openApiCustomiser() {
		return openapi -> {
			openapi
					.getPaths()
					.values()
					.forEach(pathItem -> pathItem.readOperationsMap()
							.forEach((httpMethod, operation) -> {
								ApiResponses responses = operation.getResponses();

								switch (httpMethod) {
									case GET:
										responses.addApiResponse("406",
												new ApiResponse().$ref(
														"NotAcceptable"));

										responses.addApiResponse("500",
												new ApiResponse().$ref(
														"InternalServerError"));

										responses.addApiResponse("400",
												new ApiResponse().$ref(
														"BadRequest"));
										break;

									default:
										responses.addApiResponse("500",
												new ApiResponse().$ref(
														"InternalServerError"));

										responses.addApiResponse("400",
												new ApiResponse().$ref(
														"BadRequest"));
										break;
								}
							}));
		};
	}

	private Map<String, Schema> generateSchemas() {
		Map<String, Schema> schemaMap = new HashMap<>();

		Map<String, Schema> problemSchema = ModelConverters.getInstance().read(ProblemApi.class);
		Map<String, Schema> problemObjectSchema = ModelConverters.getInstance().read(ProblemApi.Object.class);

		schemaMap.putAll(problemSchema);
		schemaMap.putAll(problemObjectSchema);

		return schemaMap;

	}

	private Map<String, ApiResponse> generateResponses() {
		Map<String, ApiResponse> apiResponseMap = new HashMap<>();

		Content content = new Content()
				.addMediaType(MediaType.APPLICATION_JSON_VALUE,
						new io.swagger.v3.oas.models.media.MediaType()
								.schema(new Schema<ProblemApi>().$ref("ProblemApi")));

		apiResponseMap.put("BadRequest", new ApiResponse()
				.description("Invalid request from client side")
				.content(content));

		apiResponseMap.put("NotAcceptable", new ApiResponse()
				.description("Server cannot produce a response that matches the list of acceptable values")
				.content(content));

		apiResponseMap.put("InternalServerError", new ApiResponse()
				.description("Internal Server Error")
				.content(content));

		return apiResponseMap;
	}
}
