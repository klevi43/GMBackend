package org.kylecodes.gm.utils;

import org.kylecodes.gm.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtil {
    public static User getPrincipalFromSecurityContext() {
        return  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof User user) {
            return user;
        }
        throw new IllegalStateException("No authenticaed user found");
    }
}
