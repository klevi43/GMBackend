package org.kylecodes.gm.contexts;

import org.kylecodes.gm.entities.User;
import org.kylecodes.gm.services.JwtService;
import org.kylecodes.gm.services.JwtServiceImpl;
import org.mockito.ArgumentMatchers;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SecurityContextForTests {
    JwtService jwtService = new JwtServiceImpl();
    AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
    Authentication auth = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    public void createSecurityContextWithAuthenticatedUser(User user) {
        when(authenticationManager.authenticate(ArgumentMatchers.any())).thenReturn(auth);
        when(auth.getPrincipal()).thenReturn(user);
        auth.setAuthenticated(true);
        when(auth.isAuthenticated()).thenReturn(true);

        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
        //when(SecurityUtil.getPrincipalFromSecurityContext()).thenReturn(user);
    }

}
