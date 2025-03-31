package org.kylecodes.gm.controllers;

import org.kylecodes.gm.dtos.AuthUserDto;
import org.kylecodes.gm.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody AuthUserDto authUserDto) {

        return authService.verify(authUserDto);
    }
}
