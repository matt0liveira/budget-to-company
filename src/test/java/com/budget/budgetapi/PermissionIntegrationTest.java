package com.budget.budgetapi;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.budget.budgetapi.core.io.Base64ProtocolResolver;
import com.budget.budgetapi.util.ResourceUtils;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = Base64ProtocolResolver.class)
@TestPropertySource("classpath:application-test.properties")
@WithMockUser(
	username = "maria.joaquina@budget.com",
	authorities = {
		"SCOPE_READ",
		"SCOPE_WRITE",
		"CONSULT_USERS_PROFILES_PERMISSIONS",
		"CHANGE_USERS_PROFILES_PERMISSIONS"
	}
)
public class PermissionIntegrationTest {
    
    @Autowired
	private MockMvc mockmvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private String jsonPermission;

	@BeforeEach
	public void setUp() {
		RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
		RestAssuredMockMvc.mockMvc(mockmvc);
		RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssuredMockMvc.basePath = "/permissions";

		jsonPermission = ResourceUtils.getContentFromResource("/json/test-permission.json");
	}

    @Test
    public void shouldReturnStatus200_WhenConsultPermissions() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldReturnStatus200AndResponseBodyCorrect_WhenFindPermissionExisting() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/{permissionId}", 1)
        .then()
            .statusCode(HttpStatus.OK.value())
            .body(Matchers.notNullValue());
    }

    @Test
    public void shouldReturnStatus201_WhenRegisterPermission() {
        given()
            .body(jsonPermission)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void shouldReturnStatus204_WhenRemovePermission() {
        given()
            .accept(ContentType.JSON)
        .when()
            .delete("/{permissionId}", 8)
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void shouldReturnStatus200_WhenUpdatePermission() {
        given()
            .body(jsonPermission)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{permissionId}", 1)
        .then()
            .statusCode(HttpStatus.OK.value());
    }

}
