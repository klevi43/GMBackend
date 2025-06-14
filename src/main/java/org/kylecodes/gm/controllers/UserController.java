package org.kylecodes.gm.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.kylecodes.gm.dtos.EmailDto;
import org.kylecodes.gm.dtos.PasswordDto;
import org.kylecodes.gm.dtos.RegisterDto;
import org.kylecodes.gm.dtos.UserDto;
import org.kylecodes.gm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public UserDto register(@RequestBody @Valid RegisterDto registerDto) {
        System.out.println(registerDto.getEmail());

        return userService.registerNewUser(registerDto);
    }

    @GetMapping("/users")
    public UserDto getUserInfo() {
        return userService.getUserInfo();
    }

    @PutMapping("/users/password/update")
    public void updateUserPassword(@RequestBody @Valid PasswordDto passwordDto, HttpServletResponse response) {
        userService.updateUserPassword(passwordDto);
        ResponseCookie cookie = ResponseCookie.from("jwtToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    @PutMapping("/users/email/update")
    public void updateUserEmail(@RequestBody @Valid EmailDto emailDto, HttpServletResponse response) {
        userService.updateUserEmail(emailDto);
        ResponseCookie cookie = ResponseCookie.from("jwtToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

    }
    @DeleteMapping("/users/delete")
    public void deleteUser(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("jwtToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        userService.deleteUser();
    }

}
