package org.kylecodes.gm.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kylecodes.gm.dtos.AuthUserDto;
import org.kylecodes.gm.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase(replace =AutoConfigureTestDatabase.Replace.NONE )
@TestPropertySource("classpath:application-test.properties")
public class AuthServiceIntegrationTest {
    private AuthService authService = new AuthServiceImpl();
    private AuthUserDto authUserDto;


    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        authUserDto = new AuthUserDto();
        authUserDto.setEmail("user@email.com");
        authUserDto.setPassword("password123");
    }

    @Test
    public void placeholder() {

    }

    @Test
    public void AuthService_verify_returnJwtToken() {
        assertThat(userRepository.existsByEmail(authUserDto.getEmail())).isTrue();


        String token = authService.verify(authUserDto);

        assertThat(token).isNotNull();
    }
}
