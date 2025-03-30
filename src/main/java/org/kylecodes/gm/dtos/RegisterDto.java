package org.kylecodes.gm.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.kylecodes.gm.constants.PasswordLen;

public class RegisterDto {
    private Long id;

    @NotNull
    @Email
    private String email;
    @NotNull
    @Size(min = PasswordLen.MIN_LENGTH, max = PasswordLen.MAX_LENGTH)
    private String password;


    private String role;


    public RegisterDto(Long id, String email, String password, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public RegisterDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotNull @Email String email) {
        this.email = email;
    }

    public @NotNull @Size(min = PasswordLen.MIN_LENGTH, max = PasswordLen.MAX_LENGTH) String getPassword() {
        return password;
    }

    public void setPassword(@NotNull @Size(min = PasswordLen.MIN_LENGTH, max = PasswordLen.MAX_LENGTH) String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
