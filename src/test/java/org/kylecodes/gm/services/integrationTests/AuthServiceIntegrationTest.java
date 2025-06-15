package org.kylecodes.gm.services.integrationTests;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kylecodes.gm.contexts.SecurityContextForTests;
import org.kylecodes.gm.dtos.AuthUserDto;
import org.kylecodes.gm.dtos.JwtDto;
import org.kylecodes.gm.exceptions.AlreadyLoggedInException;
import org.kylecodes.gm.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestPropertySource("classpath:application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // circumvent spring sec so that we don't have to add tokens
@Transactional
@Sql(scripts = {"classpath:/insertUser.sql", "classpath:/insertWorkouts.sql", "classpath:/insertExercises.sql", "classpath:/insertSets.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)//  this removes the need for setup and teardown
public class AuthServiceIntegrationTest {

    @Autowired
    AuthService authService;

    AuthUserDto authUserDto;


    private final String VALID_USER_EMAIL = "test@email.com";
    private final String VALID_USER_PASSWORD = "password123";
    private final String INVALID_USER_EMAIL = "nonexistentUser@email.com";
    private final String INVALID_USER_PASSWORD = "wrongPassword";

    SecurityContextForTests context = new SecurityContextForTests();
    @BeforeEach
    public void init() {
        authUserDto = new AuthUserDto();
        authUserDto.setEmail(VALID_USER_EMAIL);
        authUserDto.setPassword(VALID_USER_PASSWORD);


        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
    }

    @Test
    public void AuthService_Login_ReturnJwtDto() {
        JwtDto jwtDto = authService.verify(authUserDto);

        assertThat(jwtDto).isNotNull();

    }
    @Test
    public void AuthService_Login_ThrowAlreadyLoggedInException() {
        context.setSecurityContextAsIfUserIsAlreadyAuthenticated();
        assertThrows(AlreadyLoggedInException.class, () -> authService.verify(authUserDto));

    }

    @Test
    public void AuthService_Login_ThrowBadCredentialsExceptionForInvalidEmail() {
        authUserDto.setEmail(INVALID_USER_EMAIL);
        assertThrows(BadCredentialsException.class, () -> authService.verify(authUserDto));

    }

    @Test
    public void AuthService_Login_ThrowBadCredentialsExceptionforInvalidPassword() {
        authUserDto.setPassword(INVALID_USER_PASSWORD);
        assertThrows(BadCredentialsException.class, () -> authService.verify(authUserDto));

    }

}
