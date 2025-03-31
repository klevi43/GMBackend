package org.kylecodes.gm.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kylecodes.gm.constants.Roles;
import org.kylecodes.gm.dtos.AuthUserDto;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService = new AuthServiceImpl();
    private AuthUserDto authUserDto;
    private final String USERNAME = "user@email.com";
    private final String PASSWORD = "password123";
    private final Long VALID_USER_ID = 1L;
    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        authUserDto = new AuthUserDto();
        authUserDto.setId(VALID_USER_ID);
        authUserDto.setEmail(USERNAME);
        authUserDto.setPassword(PASSWORD);
        authUserDto.setRole(Roles.USER);
    }

    @Test
    public void testAuthenticationSuccess() {
        when(authenticationManager.authenticate(ArgumentMatchers.any())).thenReturn(
                new UsernamePasswordAuthenticationToken(USERNAME, PASSWORD)
        );

        assertTrue(authService.verify(authUserDto), true);
    }

    @Test
    public void AuthService_() {
        when(authenticationManager.authenticate(ArgumentMatchers.any())).thenReturn(
                new UsernamePasswordAuthenticationToken(USERNAME, PASSWORD)
        );

        assertTrue(authService.verify(authUserDto), true);
    }
}
