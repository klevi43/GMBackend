package org.kylecodes.gm.controllers;

import org.kylecodes.gm.dtos.RegisterDto;
import org.kylecodes.gm.dtos.UserDto;
import org.kylecodes.gm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/sign-up")
    public RegisterDto register(@RequestBody RegisterDto registerDto) {
        System.out.println(registerDto.getEmail());
        return userService.registerNewUser(registerDto);
    }

    @GetMapping("/users")
    public UserDto getUserInfo() {
        return userService.getUserInfo();
    }

    @DeleteMapping("/users")
    public void deleteUser() {
        userService.deleteUser();
    }

}
