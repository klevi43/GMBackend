package org.kylecodes.gm.utils;

import org.kylecodes.gm.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtil {
    public static User getPrincipalFromSecurityContext() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
        return  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static Long getPrincipalIdFromSecurityContext() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
        User user =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }
}
