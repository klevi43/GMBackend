package org.kylecodes.gm.controllers.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kylecodes.gm.dtos.AuthUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
@Sql(scripts = {"classpath:/insertUser.sql", "classpath:/insertWorkouts.sql", "classpath:/insertExercises.sql", "classpath:/insertSets.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)//  this removes the need for setup and teardown
public class AuthControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    AuthUserDto validUser;
    AuthUserDto nonexistentUser;
    AuthUserDto badPwUser;
    @BeforeEach
    public void init() {
        validUser = new AuthUserDto();
        validUser.setEmail("test1234@email.com");
        validUser.setPassword("password123");
        nonexistentUser = new AuthUserDto();
        nonexistentUser.setEmail("nonexistentUser@email.com");
        nonexistentUser.setPassword("password123");
        badPwUser = new AuthUserDto();
        badPwUser.setEmail("user4345345@email.com");
        badPwUser.setPassword("password12");
    }
    @Test
    public void AuthController_Login_ReturnJwtDto() throws Exception {
        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
        ResultActions result = mockMvc.perform(post("/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(validUser)));

        result.andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void AuthController_LoginWithNonExistentEmail_ThrowBadCredentialsException() throws Exception {
        ResultActions result = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nonexistentUser)));

        result.andExpect(status().is4xxClientError()).andDo(MockMvcResultHandlers.print());;
    }
    @Test
    public void AuthContoller_LoginWithWrongPassword_ThrowBadCredentialsException() throws Exception {
        ResultActions result = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(badPwUser)));

        result.andExpect(status().is4xxClientError()).andDo(MockMvcResultHandlers.print());;
    }
}
