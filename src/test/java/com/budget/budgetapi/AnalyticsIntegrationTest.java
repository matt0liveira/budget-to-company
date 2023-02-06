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
            "CONSULT_REPORTS"
        }
)
public class AnalyticsIntegrationTest {
    
    @Autowired
    private MockMvc mockmvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @BeforeEach
	public void setUp() {
		RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
		RestAssuredMockMvc.mockMvc(mockmvc);
		RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssuredMockMvc.basePath = "/analytics";
	}

    @Test
    public void shouldReturnStatus200AndResponseBodyCorrect_WhenConsultTotalTransactions() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/total-transactions")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body(Matchers.notNullValue());
    }

    @Test
    public void shouldReturnStatus200AndResponseBodyCorrect_WhenConsultTotalTransactionsLastFourYears() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/total-transactions-last-four-years")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body(Matchers.notNullValue());
    }

    @Test
    public void shouldReturnStatus200AndResponseBodyCorrect_WhenConsultTotalTransactionsByWeekCurrent() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/total-transactions-by-week-current")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body(Matchers.notNullValue());
    }

    @Test
    public void shouldReturnStatus200AndResponseBodyCorrect_WhenConsultTotalTransactionsByMonth() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/total-transactions-by-month")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body(Matchers.notNullValue());
    }

    @Test
    public void shouldReturnStatus200AndResponseBodyCorrect_WhenConsultTotalTransactionsByDate() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/total-transactions-by-date")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body(Matchers.notNullValue());
    }

    @Test
    public void shouldReturnStatus200AndResponseBodyCorrect_WhenConsultTotalTransactionsByCurrentDate() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/total-transactions-by-current-date")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body(Matchers.notNullValue());
    }

}
