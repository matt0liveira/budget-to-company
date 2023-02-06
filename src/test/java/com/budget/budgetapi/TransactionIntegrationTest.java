// package com.budget.budgetapi;

// import static org.assertj.core.api.Assertions.assertThat;
// import org.hamcrest.Matchers;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.HttpStatus;
// import org.springframework.security.test.context.support.WithMockUser;
// import org.springframework.test.context.ContextConfiguration;
// import org.springframework.test.context.TestPropertySource;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.web.context.WebApplicationContext;

// import com.budget.budgetapi.core.io.Base64ProtocolResolver;
// import com.budget.budgetapi.domain.model.ChildCategory;
// import com.budget.budgetapi.domain.model.Transaction;
// import com.budget.budgetapi.domain.model.enums.TypeTransaction;
// import com.budget.budgetapi.domain.service.TransactionService;
// import com.budget.budgetapi.util.ResourceUtils;

// import io.restassured.http.ContentType;
// import io.restassured.module.mockmvc.RestAssuredMockMvc;
// import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

// import java.math.BigDecimal;
// import java.sql.Date;
// import java.time.LocalDate;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
// @AutoConfigureMockMvc
// @ContextConfiguration(initializers = Base64ProtocolResolver.class)
// @TestPropertySource("classpath:application-test.properties")
// @WithMockUser(
// 	username = "maria.joaquina@budget.com",
// 	authorities = {
// 		"SCOPE_READ",
// 		"SCOPE_WRITE",
// 		"CONSULT_TRANSACTIONS",
// 		"CHANGE_TRANSACTIONS"
// 	}
// )
// public class TransactionIntegrationTest {
    
//     @Autowired
// 	private MockMvc mockmvc;

// 	@Autowired
// 	private WebApplicationContext webApplicationContext;

// 	private String jsonTransaction;

//     @Autowired
//     private TransactionService transactionService;

// 	@BeforeEach
// 	public void setUp() {
// 		RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
// 		RestAssuredMockMvc.mockMvc(mockmvc);
// 		RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();
// 		RestAssuredMockMvc.basePath = "/transactions";

// 		jsonTransaction = ResourceUtils.getContentFromResource("/json/test-transaction.json");
// 	}

//     @Test
//     public void shouldReturnStatus200_WhenConsulTransactions() {
//         given()
//             .accept(ContentType.JSON)
//         .when()
//             .get()
//         .then()
//             .statusCode(HttpStatus.OK.value());
//     }

//     @Test
//     public void shouldReturnStatus200AndResponseBodyCorrect_WhenFindTransactionExisting() {
//         given()
//             .accept(ContentType.JSON)
//         .when()
//             .get("/{transactionCode}", "24faa01b-c97b-47a6-be67-b054bfe78d55")
//         .then()
//             .statusCode(HttpStatus.OK.value())
//             .body(Matchers.notNullValue());
//     }

//     @Test
//     public void shouldReturnStatus201_WhenRegisterTransaction() {
//         given()
//             .body(jsonTransaction)
//             .contentType(ContentType.JSON)
//             .accept(ContentType.JSON)
//         .when()
//             .post()
//         .then()
//             .statusCode(HttpStatus.CREATED.value());
//     }

//     @Test
//     public void shouldReturnStatus204_WhenRemoveTransaction() {
//         given()
//             .accept(ContentType.JSON)
//         .when()
//             .delete("/{transactionCode}", "24faa01b-c97b-47a6-be67-b054bfe78d55")
//         .then()
//             .statusCode(HttpStatus.NO_CONTENT.value());
//     }

//     @Test
//     public void shouldReturnStatus200_WhenUpdateTransaction() {
//         given()
//             .body(jsonTransaction)
//             .contentType(ContentType.JSON)
//             .accept(ContentType.JSON)
//         .when()
//             .patch("/{transactionCode}", "a9f7a3f0-3dc1-46de-b254-7b02ae13926d")
//         .then()
//             .statusCode(HttpStatus.OK.value());
//     }

//     @Test
//     public void shouldSucceed_WhenDataIsCorrect() {
//         Transaction newTransaction = new Transaction();
//         newTransaction.setValue(BigDecimal.valueOf(60));
//         newTransaction.setType(TypeTransaction.EXPENSE);
//         newTransaction.setCategory(new ChildCategory());
//         newTransaction.getCategory().setId(1L);
//         newTransaction.setDescription("teste");
//         newTransaction.setDate(Date.valueOf(LocalDate.now()));

//         newTransaction = transactionService.save(newTransaction);

//         assertThat(newTransaction.getValue()).isPositive();
//         assertThat(newTransaction.getType()).isNotNull();
//         assertThat(newTransaction.getCategory().getId()).isNotNull();
//         assertThat(newTransaction.getDescription()).isNotBlank();
//         assertThat(newTransaction.getDate()).isNotNull();
//     }

// }
