package org.kylecodes.gm.controllers;

import org.kylecodes.gm.dtos.PageDto;
import org.kylecodes.gm.dtos.UserDto;
import org.kylecodes.gm.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/admin/users")
    public PageDto<UserDto> getAllUsers(@RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        return adminService.getAllUsers(pageNo, pageSize);
    }

    @PutMapping("/admin/users/promote")
    public UserDto promoteToAdmin(@RequestParam Long userId) {
        return adminService.promoteToAdmin(userId);
    }
    @PutMapping("/admin/users/demote")
    public UserDto demoteToUser(@RequestParam Long userId) {
        return adminService.demoteToUser(userId);
    }
    @DeleteMapping("/admin/users/delete")
    public void deleteUser(@RequestParam Long userId) {
        adminService.deleteUser(userId);
    }
}
