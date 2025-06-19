package org.kylecodes.gm.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsrfController {
    @GetMapping("/csrf-cookie")
    public ResponseEntity<Void> getCsrfCookie(CsrfToken csrfToken) {
        // Just accessing this will trigger the cookie to be set via CsrfTokenRepository
        return ResponseEntity.noContent().build();
    }
}
