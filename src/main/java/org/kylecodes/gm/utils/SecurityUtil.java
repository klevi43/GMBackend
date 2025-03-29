package org.kylecodes.gm.utils;

import org.kylecodes.gm.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtil {
    public static User getPrincipalFromSecurityContext() {
        return  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
