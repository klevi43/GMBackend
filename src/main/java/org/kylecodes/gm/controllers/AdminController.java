package org.kylecodes.gm.controllers;

import org.kylecodes.gm.dtos.PageDto;
import org.kylecodes.gm.dtos.UserDto;
import org.kylecodes.gm.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/admin/users")
    public PageDto<UserDto> getAllUsers(@RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        return adminService.getAllUsers(pageNo, pageSize);
    }
}
