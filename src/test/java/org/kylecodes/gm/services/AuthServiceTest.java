package org.kylecodes.gm.services;

import org.junit.Before;
import org.junit.Test;
import org.kylecodes.gm.constants.Roles;
import org.kylecodes.gm.contexts.SecurityContextForTests;
import org.kylecodes.gm.dtos.AuthUserDto;
import org.kylecodes.gm.entities.User;
import org.kylecodes.gm.filters.JwtFilter;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class AuthServiceTest {

//
//@Mock
//    private AuthenticationManager authenticationManager;


    @Mock
    AuthenticationManager authenticationManager;

    @MockitoBean
    JwtFilter jwtFilter;

    @InjectMocks
    private AuthService authService = mock(AuthServiceImpl.class);

    private User user;

    private AuthUserDto authUserDto;
    private final String USERNAME = "user@email.com";
    private final String PASSWORD = "password123";
    private final Long VALID_USER_ID = 1L;
    private final Integer VALID_TOKEN_LENGTH = 72;
    private final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQGVtYWlsLmNvbSIsImlhdCI6MTc0MzY3NDEzMSwiZXhwIjoxNzQzNjc1MjExfQ.VJ3D39dk8TvwNTIyHyYXA6b0sp8Ubr3Rx26_7UZd9cY";
    private SecurityContextForTests context = new SecurityContextForTests();
    @Before
    public void init() {
        authUserDto = new AuthUserDto();
        authUserDto.setId(VALID_USER_ID);
        authUserDto.setEmail(USERNAME);
        authUserDto.setPassword(PASSWORD);
        authUserDto.setRole(Roles.USER);
        user = new User();
        user.setId(VALID_USER_ID);
        user.setEmail(USERNAME);
        user.setPassword(PASSWORD);
        user.setRole(Roles.USER);
        //context.createSecurityContextWithUser(user);
    }


    @Test
    public void AuthService_Verify_ReturnJwt() throws InstantiationException, IllegalAccessException {
//        when(authenticationManager.authenticate(ArgumentMatchers.any())).thenReturn(
//                new UsernamePasswordAuthenticationToken(USERNAME, PASSWORD)
//        );




        String token = authService.verify(authUserDto);
        System.out.println(token);
        assertThat(token).isNotNull();
        assertThat(token.length()).isEqualTo(VALID_TOKEN_LENGTH);
    }


}
