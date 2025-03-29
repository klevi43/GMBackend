package org.kylecodes.gm.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kylecodes.gm.dtos.UserDto;
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
    private UserDto userDto;
    String username = "user1";
    String password = "pass123";
    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail(username);
        userDto.setPassword(password);
        userDto.setRole("ROLE_USER");
    }

    @Test
    public void testAuthenticationSuccess() {
        when(authenticationManager.authenticate(ArgumentMatchers.any())).thenReturn(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        assertTrue(authService.verify(userDto), true);
    }
}
