package org.kylecodes.gm.contexts;

import org.kylecodes.gm.entities.User;
import org.kylecodes.gm.services.JwtService;
import org.kylecodes.gm.services.JwtServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SecurityContextForTests {
    JwtService jwtService = new JwtServiceImpl();
    AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    public void createSecurityContextToReturnAuthenticatedUser(User user) {
        when(authentication.getPrincipal()).thenReturn(user);
        authentication.setAuthenticated(true);
        when(authentication.isAuthenticated()).thenReturn(true);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        //when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
    }

    public void setSecurityContextAsIfUserIsAlreadyAuthenticated() {


        authentication.setAuthenticated(true);


        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

}
