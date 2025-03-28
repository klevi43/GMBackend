package org.kylecodes.gm.helpers;

import org.kylecodes.gm.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityContext {
    public static User getPrincipalFromSecurityContext() {
        return  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
