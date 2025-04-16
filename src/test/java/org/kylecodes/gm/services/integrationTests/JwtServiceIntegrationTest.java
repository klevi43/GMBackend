package org.kylecodes.gm.services.integrationTests;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.kylecodes.gm.services.JwtService;
import org.kylecodes.gm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestPropertySource("classpath:application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // circumvent spring sec so that we don't have to add tokens
@Transactional
@Sql(scripts = {"classpath:/insertWorkouts.sql", "classpath:/insertExercises.sql", "classpath:/insertSets.sql"})//  this removes the need for setup and teardown with jdbc
public class JwtServiceIntegrationTest {

    @Autowired
    private JwtService jwtService;

    @Autowired
    UserService userService;
    private final String VALID_USER_EMAIL = "test@email.com";
    private final String INVALID_USER_EMAIL = "nonexistentUser@email.com";
    @Test
    public void JwtService_GenerateToken_ReturnTokenAsString() {
        String token = jwtService.generateToken(VALID_USER_EMAIL);
        assertThat(token).isNotNull();
    }

    @Test
    public void JwtService_GenerateToken_ThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> jwtService.generateToken(null));
    }




    @Test
    public void JwtService_ExtractEmail_ReturnExtractedEmailAsString() {
        String token = jwtService.generateToken(VALID_USER_EMAIL);
        String extractedEmail = jwtService.extractEmail(token);

        assertThat(token).isNotNull();
        assertThat(extractedEmail).isNotNull();
    }




    @Test
    public void JwtService_ExtractEmail_Returnsomsdf() {

       assertThrows(IllegalArgumentException.class, () -> jwtService.extractEmail(null));
    }

    @Test
    public void JwtService_ValidateToken_ReturnTokenIsValid() {
        String token = jwtService.generateToken(VALID_USER_EMAIL);
        String extractedEmail = jwtService.extractEmail(token);
        boolean result = jwtService.validateToken(token, userService.loadUserByUsername(VALID_USER_EMAIL));

        assertThat(token).isNotNull();
        assertThat(extractedEmail).isNotNull();
        assertThat(result).isTrue();
    }

    @Test
    public void JwtService_ValidatedToken_ReturnTokenNotValid() {
        String token = jwtService.generateToken(INVALID_USER_EMAIL);
        assertThrows(UsernameNotFoundException.class, () -> jwtService.validateToken(token, userService.loadUserByUsername(INVALID_USER_EMAIL)));

    }


}
