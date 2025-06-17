package org.kylecodes.gm.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.kylecodes.gm.dtos.AuthUserDto;
import org.kylecodes.gm.dtos.JwtDto;
import org.kylecodes.gm.entities.User;
import org.kylecodes.gm.responses.LoginResponse;
import org.kylecodes.gm.services.AuthService;
import org.kylecodes.gm.utils.HttpOnlyCookieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration;

    @PostMapping("/auth/logout")
    public void logout(HttpServletResponse response) {
        ResponseCookie cookie = HttpOnlyCookieHelper.removeJwtFromCookie();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
    @PostMapping(value = "/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthUserDto authUserDto, HttpServletResponse response) {

        JwtDto jwtDto = authService.verify(authUserDto);
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ResponseCookie cookie = HttpOnlyCookieHelper.addJwtToCookie(jwtDto);
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(new LoginResponse(authenticatedUser.getUsername(), authenticatedUser.getRole(),
                true));
    }
    @GetMapping("/auth/me")
    public ResponseEntity<?> getAuthenticatedUser(HttpServletResponse response) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LoginResponse res = new LoginResponse();
        res.setUserEmail(authenticatedUser.getUsername());
        res.setUserRole(authenticatedUser.getRole());
        res.setIsAuthenticated(SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
        return ResponseEntity.ok(res);
    }


}
