package org.kylecodes.gm.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.kylecodes.gm.constants.PasswordLen;

public class PasswordDto {
    @NotNull
    @Size(min = PasswordLen.MIN_LENGTH, max = PasswordLen.MAX_LENGTH)
    private String currentPassword;
    @NotNull
    @Size(min = PasswordLen.MIN_LENGTH, max = PasswordLen.MAX_LENGTH)
    private String newPassword;
    @NotNull
    @Size(min = PasswordLen.MIN_LENGTH, max = PasswordLen.MAX_LENGTH)
    private String confirmNewPassword;

    public PasswordDto(String currentPassword, String newPassword, String confirmNewPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
    }

    public PasswordDto() {
    }

    public @NotNull @Size(min = PasswordLen.MIN_LENGTH, max = PasswordLen.MAX_LENGTH) String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(@NotNull @Size(min = PasswordLen.MIN_LENGTH, max = PasswordLen.MAX_LENGTH) String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public @NotNull @Size(min = PasswordLen.MIN_LENGTH, max = PasswordLen.MAX_LENGTH) String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(@NotNull @Size(min = PasswordLen.MIN_LENGTH, max = PasswordLen.MAX_LENGTH) String newPassword) {
        this.newPassword = newPassword;
    }

    public @NotNull @Size(min = PasswordLen.MIN_LENGTH, max = PasswordLen.MAX_LENGTH) String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(@NotNull @Size(min = PasswordLen.MIN_LENGTH, max = PasswordLen.MAX_LENGTH) String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
