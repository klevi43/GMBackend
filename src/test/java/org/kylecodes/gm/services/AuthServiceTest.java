package org.kylecodes.gm.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kylecodes.gm.constants.Roles;
import org.kylecodes.gm.dtos.AuthUserDto;
import org.kylecodes.gm.entities.User;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    Authentication authentication = mock(Authentication.class);
    SecurityContext context = mock(SecurityContext.class);


    @InjectMocks
    private AuthService authService = new AuthServiceImpl();
    private User user;

    private AuthUserDto authUserDto;
    private final String USERNAME = "user@email.com";
    private final String PASSWORD = "password123";
    private final Long VALID_USER_ID = 1L;
    private final Integer VALID_TOKEN_LENGTH = 72;


    @Before
    public void init() {

        user = new User();
        user.setId(VALID_USER_ID);
        user.setEmail(USERNAME);
        user.setPassword(PASSWORD);
        user.setRole(Roles.USER);
        authentication.setAuthenticated(false);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

    }

    @Test
    public void AuthService_Verify_ReturnJwt() {
        authUserDto = new AuthUserDto();
        authUserDto.setId(VALID_USER_ID);
        authUserDto.setEmail(USERNAME);
        authUserDto.setPassword(PASSWORD);
        authUserDto.setRole(Roles.USER);

        context.setAuthentication(authentication);
        when(context.getAuthentication()).thenReturn(authentication);
        when(authenticationManager.authenticate(ArgumentMatchers.any())).thenReturn(
                new UsernamePasswordAuthenticationToken(authUserDto.getEmail(), authUserDto.getPassword())
        );
        when(authentication.isAuthenticated()).thenReturn(true);




        //String token = authService.verify(authUserDto);
//        System.out.println(token);
//        assertThat(token).isNotNull();
//        assertThat(token.length()).isEqualTo(VALID_TOKEN_LENGTH);
    }

    @Test
    public void AuthService_() {
        when(authenticationManager.authenticate(ArgumentMatchers.any())).thenReturn(
                new UsernamePasswordAuthenticationToken(USERNAME, PASSWORD)
        );

//        assertTrue(authService.verify(authUserDto), true);
    }
}
