package org.kylecodes.gm.controllers.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kylecodes.gm.constants.EmailAlreadyExists;
import org.kylecodes.gm.constants.PasswordErrorMsg;
import org.kylecodes.gm.constants.Roles;
import org.kylecodes.gm.contexts.SecurityContextForTests;
import org.kylecodes.gm.dtos.AuthUserDto;
import org.kylecodes.gm.dtos.RegisterDto;
import org.kylecodes.gm.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureMockMvc(addFilters = false) // circumvent spring sec so that we don't have to add tokens
@Transactional
public class UserControllerIntegrationTest {

    private final String EMAIL = "user@email.com";
    private final String PASSWORD = "password123";
    private final String DIFFERENT_PASSWORD = "DifferentPassword";
    private final String CONFIRM_PASSWORD = PASSWORD;
    private final String UPDATE_EMAIL = "updated@email.com";
    private final String UPDATE_PASSWORD = "updatePassword";
    private final String ALREADY_EXISTENT_EMAIL = EMAIL;
    private final String NEW_USER_EMAIL = "newUser@email.com";


    AuthUserDto validUser;
    RegisterDto registerDto;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    SecurityContextForTests context;

    @BeforeEach
    public void init() {

        registerDto = new RegisterDto();
        registerDto.setEmail(NEW_USER_EMAIL);
        registerDto.setPassword(PASSWORD);
        registerDto.setConfirmPassword(CONFIRM_PASSWORD);
        validUser = new AuthUserDto();
        validUser.setId(1L);
        validUser.setEmail(EMAIL);
        validUser.setPassword(PASSWORD);
        validUser.setRole(Roles.USER);

        User user = new User();
        user.setId(validUser.getId());
        user.setEmail(validUser.getEmail());
        user.setPassword(validUser.getPassword());
        user.setRole(validUser.getRole());
        context = new SecurityContextForTests();
        context.createSecurityContextToReturnAuthenticatedUser(user);

    }

    @Test
    public void UserController_GetUserDetails_ReturnUserDToOfLoggedInUser() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/users"));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.email", CoreMatchers.is(validUser.getEmail())))
                .andExpect(jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(jsonPath("$.role", CoreMatchers.is(validUser.getRole())));

    }

    @Test
    public void UserController_Register_ReturnNewRegisteredUserDto() throws Exception {
        registerDto.setConfirmPassword(CONFIRM_PASSWORD);
        ResultActions resultActions = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(registerDto)));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.notNullValue()))
                .andExpect(jsonPath("$.email", CoreMatchers.is(registerDto.getEmail())))
                .andExpect(jsonPath("$.role", CoreMatchers.is("ROLE_" + Roles.USER)));
    }

    @Test
    public void UserController_Register_ThrowEmailAlreadyExistsException() throws Exception {
        registerDto.setEmail(ALREADY_EXISTENT_EMAIL);
        ResultActions resultActions = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(registerDto)));

        resultActions.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", CoreMatchers.is(EmailAlreadyExists.ERROR_MSG)));
    }

    @Test
    public void UserController_Register_ThrowPasswordAndConfirmPasswordNotEqualException() throws Exception {
        registerDto.setConfirmPassword(DIFFERENT_PASSWORD);
        ResultActions resultActions = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(registerDto)));

        resultActions.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", CoreMatchers.is(PasswordErrorMsg.PASSWORD_AND_CONFIRM_PASSWORD_MUST_BE_EQUAL_VALUES)));
    }

    @Test
    public void UserController_UpdateUserInfo_ReturnUpdatedUserDto() throws Exception {
        validUser.setEmail(UPDATE_EMAIL);
        validUser.setPassword(UPDATE_PASSWORD);
        ResultActions resultActions = mockMvc.perform(put("/users/update")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(validUser)));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.email", CoreMatchers.is(UPDATE_EMAIL)));
    }


    @Test
    public void UserController_DeleteUser_ReturnNothing() throws Exception {
        ResultActions resultActions = mockMvc.perform(delete("/users/delete"));

        resultActions.andExpect(status().isOk());
    }

}
