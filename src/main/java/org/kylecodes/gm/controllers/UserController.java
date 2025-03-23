package org.kylecodes.gm.controllers;

import org.kylecodes.gm.dtos.RegisterDto;
import org.kylecodes.gm.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserServiceImpl userService;
    @PostMapping("/sign-up")
    public RegisterDto register(@RequestBody RegisterDto registerDto) {
        return userService.registerNewUser(registerDto);
    }
}
