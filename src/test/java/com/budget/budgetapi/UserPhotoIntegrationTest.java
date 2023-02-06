package com.budget.budgetapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.budget.budgetapi.core.io.Base64ProtocolResolver;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

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
public class UserPhotoIntegrationTest {
    
    @Autowired
    private MockMvc mockmvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @BeforeEach
	public void setUp() {
		RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
		RestAssuredMockMvc.mockMvc(mockmvc);
		RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssuredMockMvc.basePath = "/users";
	}

    @Test
    public void shoulReturnStatus200_WhenUpdatePhotoUser() throws IOException {        
        MockMultipartFile file = new MockMultipartFile(
            "file",
        "user.jpeg", 
        MediaType.MULTIPART_FORM_DATA_VALUE,
        "".getBytes());

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        try {
            mockMvc
                .perform(
                    multipart("users/{userId}/photo", 1)
                    .file(file)
                ).andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
