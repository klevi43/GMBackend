package org.kylecodes.gm.controllers;

import org.kylecodes.gm.dtos.UserDto;
import org.kylecodes.gm.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/admin/users")
    public List<UserDto> getAllUsers() {
        return adminService.getAllUsers();
    }
}
