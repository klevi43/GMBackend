package org.kylecodes.gm.dtos;

import jakarta.validation.constraints.Email;

public class UserDto {
    private Long id;
    @Email
    private String email;
    private String role;

    public UserDto(Long id, String email, String role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public UserDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
