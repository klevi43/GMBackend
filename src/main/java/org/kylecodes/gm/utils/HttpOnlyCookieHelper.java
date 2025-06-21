package org.kylecodes.gm.utils;

import org.kylecodes.gm.dtos.JwtDto;
import org.springframework.http.ResponseCookie;

public final class HttpOnlyCookieHelper {
    public static ResponseCookie addJwtToCookie(JwtDto jwtDto) {
        return ResponseCookie.from("jwtToken", jwtDto.getPayload())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(3600)
                .sameSite("None")
                .build();
    }

    public static ResponseCookie removeJwtFromCookie() {
        return ResponseCookie.from("jwtToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("None")
                .build();
    }
}
