package org.kylecodes.gm.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class EmailDto {
    @NotNull
    @Email
    private String currentEmail;
    @NotNull
    @Email
    private String newEmail;

    public EmailDto(String currentEmail, String newEmail) {
        this.currentEmail = currentEmail;
        this.newEmail = newEmail;
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
}
