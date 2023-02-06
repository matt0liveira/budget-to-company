// package com.budget.budgetapi;

// import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

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
// import com.budget.budgetapi.domain.model.User;
// import com.budget.budgetapi.domain.service.CategoryService;
// import com.budget.budgetapi.util.ResourceUtils;

// import io.restassured.http.ContentType;
// import io.restassured.module.mockmvc.RestAssuredMockMvc;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
// @AutoConfigureMockMvc
// @ContextConfiguration(initializers = Base64ProtocolResolver.class)
// @TestPropertySource("classpath:application-test.properties")
// @WithMockUser(
// 	username = "maria.joaquina@budget.com",
// 	authorities = {
// 		"SCOPE_READ",
// 		"SCOPE_WRITE",
// 		"CONSULT_CATEGORIES",
// 		"CHANGE_CATEGORIES"
// 	}
// )
// public class CategoryIntegrationTest {

// 	@Autowired
// 	private MockMvc mockmvc;

// 	@Autowired
// 	private WebApplicationContext webApplicationContext;

// 	private String jsonCategory;

// 	@Autowired
// 	private CategoryService categoryService;

// 	@BeforeEach
// 	public void setUp() {
// 		RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
// 		RestAssuredMockMvc.mockMvc(mockmvc);
// 		RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();
// 		RestAssuredMockMvc.basePath = "/categories";

// 		jsonCategory = ResourceUtils.getContentFromResource("/json/test-category.json");
// 	}

// 	@Test
// 	public void shouldReturnStatus200_WhenListCategories() {
// 		given()
// 			.accept(ContentType.JSON)
// 		.when()
// 			.get()
// 		.then()
// 			.statusCode(HttpStatus.OK.value());
// 	}

// 	@Test
// 	public void shouldReturnStatus200AndResponseBodyCorrect_WhenFindCategoryExisting() {
// 		given()
// 			.accept(ContentType.JSON)
// 		.when()
// 			.get("/{categoryId}", 1)
// 		.then()
// 			.statusCode(HttpStatus.OK.value())
// 			.body(Matchers.notNullValue());
// 	}

// 	@Test
// 	public void shouldReturnStatus201_WhenRegisterCategory() {
// 		given()
// 			.body(jsonCategory)
// 			.contentType(ContentType.JSON)
// 			.accept(ContentType.JSON)
// 		.when()
// 			.post()
// 		.then()
// 			.statusCode(HttpStatus.CREATED.value());
// 	}

// 	@Test
// 	public void shouldReturnStatus204_WhenInactivateCategory() {
// 		given()
// 			.accept(ContentType.JSON)
// 		.when()
// 			.put("/{categoryId}/inactive", 1)
// 		.then()
// 			.statusCode(HttpStatus.NO_CONTENT.value());
// 	}

// 	@Test
// 	public void shouldReturnStatus204_WhenActivateCategory() {
// 		given()
// 			.accept(ContentType.JSON)
// 		.when()
// 			.delete("/{categoryId}/inactive", 1)
// 		.then()
// 			.statusCode(HttpStatus.NO_CONTENT.value());
// 	}

// 	@Test
// 	public void shouldReturnStatus204_WhenRemoveCategory() {
// 		given()
// 			.accept(ContentType.JSON)
// 		.when()
// 			.delete("/{categoryId}", 3)
// 		.then()
// 			.statusCode(HttpStatus.NO_CONTENT.value());
// 	}

// 	@Test
// 	public void shoulReturnStatus200_WhenUpdateCategory() {
// 		given()
// 			.body(jsonCategory)
// 			.accept(ContentType.JSON)
// 			.contentType(ContentType.JSON)
// 		.when()
// 			.put("/{categoryId}", 2)
// 		.then()
// 			.statusCode(HttpStatus.OK.value());
// 	}

// 	@Test
// 	public void shouldSucceed_WhenDataIsCorrect() {
// 		ChildCategory newCategory = new ChildCategory();
// 		newCategory.setDescription("Teste");
// 		newCategory.setColor("#000");
// 		newCategory.setUser(new User());
// 		newCategory.getUser().setId(1L);

// 		newCategory = categoryService.save(newCategory);

// 		assertThat(newCategory.getId()).isNotNull();
// 		assertThat(newCategory.getDescription()).isNotBlank();
// 		assertThat(newCategory.getColor()).isNotBlank();
// 		assertThat(newCategory.getUser().getId()).isNotNull();
// 	}

// }
