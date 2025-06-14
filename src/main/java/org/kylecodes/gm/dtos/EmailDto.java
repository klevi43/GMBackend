package org.kylecodes.gm.dtos;

import jakarta.validation.constraints.*;
import org.kylecodes.gm.constants.PasswordLen;

public class EmailDto {
    @NotNull
    @Email
    private String currentEmail;
    @NotNull
    @Email
    private String newEmail;

    @NotNull
    @Size(min = PasswordLen.MIN_LENGTH, max = PasswordLen.MAX_LENGTH)
    private String password;

    public EmailDto(String currentEmail, String newEmail, String password) {
        this.currentEmail = currentEmail;
        this.newEmail = newEmail;
        this.password = password;
    }

    public EmailDto() {
    }

    public @NotNull @Email String getCurrentEmail() {
        return currentEmail;
    }

    public void setCurrentEmail(@NotNull @Email String currentEmail) {
        this.currentEmail = currentEmail;
    }

    public @NotNull @Email String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(@NotNull @Email String newEmail) {
        this.newEmail = newEmail;
    }

    public @NotNull @Size(min = PasswordLen.MIN_LENGTH, max = PasswordLen.MAX_LENGTH) String getPassword() {
        return password;
    }

    public void setPassword(@NotNull @Size(min = PasswordLen.MIN_LENGTH, max = PasswordLen.MAX_LENGTH) String password) {
        this.password = password;
    }
}
