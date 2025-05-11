package org.kylecodes.gm.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.kylecodes.gm.constants.TokenDuration;
import org.kylecodes.gm.dtos.AuthUserDto;
import org.kylecodes.gm.dtos.JwtDto;
import org.kylecodes.gm.responses.LoginResponse;
import org.kylecodes.gm.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody AuthUserDto authUserDto, HttpServletResponse response) {

        JwtDto jwtDto = authService.verify(authUserDto);

        ResponseCookie cookie = ResponseCookie.from("jwtToken", jwtDto.getPayload())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(TokenDuration.THIRTY_MINS)
                .sameSite("Strict")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(new LoginResponse("Success"));
    }
}
