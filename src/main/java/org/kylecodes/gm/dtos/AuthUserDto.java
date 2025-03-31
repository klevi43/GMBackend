package org.kylecodes.gm.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.kylecodes.gm.constants.PasswordLen;

public class AuthUserDto {
    private Long id;
    @Email
    private String email;
    @Size(min = PasswordLen.MIN_LENGTH, max = PasswordLen.MAX_LENGTH)
    private String password;
    private String role;

    public AuthUserDto(Long id, String email, String password, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public AuthUserDto() {
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

    public @Size(min = PasswordLen.MIN_LENGTH, max = PasswordLen.MAX_LENGTH) String getPassword() {
        return password;
    }

    public void setPassword(@Size(min = PasswordLen.MIN_LENGTH, max = PasswordLen.MAX_LENGTH) String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
