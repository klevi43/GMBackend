package org.kylecodes.gm.controllers;

import org.kylecodes.gm.dtos.RegisterDto;
import org.kylecodes.gm.dtos.UserDto;
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
        System.out.println(registerDto.getEmail());
        return userService.registerNewUser(registerDto);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDto user) {
        System.out.println(user);
        return "Success";
    }
}
