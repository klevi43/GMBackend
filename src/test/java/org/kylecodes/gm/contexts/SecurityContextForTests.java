package org.kylecodes.gm.contexts;

import org.kylecodes.gm.entities.User;
import org.kylecodes.gm.services.AuthService;
import org.kylecodes.gm.services.AuthServiceImpl;
import org.kylecodes.gm.utils.SecurityUtil;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SecurityContextForTests {
    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService = new AuthServiceImpl();
    Authentication auth = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    public void createSecurityContextWithUser(User user) {
        
        when(auth.getPrincipal()).thenReturn(user);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityUtil.getPrincipalFromSecurityContext()).thenReturn(user);
    }
}
