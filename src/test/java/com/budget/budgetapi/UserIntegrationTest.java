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
            "SCOPE_WRITE",
            "SCOPE_READ",
            "CHANGE_USERS_PROFILES_PERMISSIONS",
            "CONSULT_USERS_PROFILES_PERMISSIONS"
        }
)
public class UserIntegrationTest {

    @Autowired
    private MockMvc mockmvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private String jsonUser;

    private String jsonUserUpdate;
    
    @BeforeEach
	public void setUp() {
		RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
		RestAssuredMockMvc.mockMvc(mockmvc);
		RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssuredMockMvc.basePath = "/users";

		jsonUser = ResourceUtils.getContentFromResource("/json/test-user.json");
        jsonUserUpdate = ResourceUtils.getContentFromResource("/json/test-userUpdate.json");
	}

    @Test
    public void shouldReturnStatus200_WhenConsultUsers() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldReturnStatus200AndResponseBody_WhenDataIsCorrect() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/{userId}", 1)
        .then()
            .statusCode(HttpStatus.OK.value())
            .body(Matchers.notNullValue());
    }

    @Test
    public void shouldReturnStatus201_WhenRegisterUser() {
        given()
            .body(jsonUser)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void shouldReturnStatus204_WhenRemoveUser() {
        given()
            .accept(ContentType.JSON)
        .when()
            .delete("/{userId}", 4)
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void shouldReturnStatus200_WhenUpdateUser() {
        given()
            .body(jsonUserUpdate)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{userId}", 1)
        .then()
            .statusCode(HttpStatus.OK.value());
    }

}
