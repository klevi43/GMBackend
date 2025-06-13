package org.kylecodes.gm.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.kylecodes.gm.constants.PasswordLen;

public class PasswordDto {
    @NotNull
    @Min(PasswordLen.MIN_LENGTH)
    @Max(PasswordLen.MAX_LENGTH)
    private String currentPassword;
    @NotNull
    @Min(PasswordLen.MIN_LENGTH)
    @Max(PasswordLen.MAX_LENGTH)
    private String newPassword;
    @NotNull
    @Min(PasswordLen.MIN_LENGTH)
    @Max(PasswordLen.MAX_LENGTH)
    private String confirmNewPassword;

    public PasswordDto(String currentPassword, String newPassword, String confirmNewPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
    }

    public PasswordDto() {
    }

    public @NotNull @Min(PasswordLen.MIN_LENGTH) @Max(PasswordLen.MAX_LENGTH) String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(@NotNull @Min(PasswordLen.MIN_LENGTH) @Max(PasswordLen.MAX_LENGTH) String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public @NotNull @Min(PasswordLen.MIN_LENGTH) @Max(PasswordLen.MAX_LENGTH) String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(@NotNull @Min(PasswordLen.MIN_LENGTH) @Max(PasswordLen.MAX_LENGTH) String newPassword) {
        this.newPassword = newPassword;
    }

    public @NotNull @Min(PasswordLen.MIN_LENGTH) @Max(PasswordLen.MAX_LENGTH) String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(@NotNull @Min(PasswordLen.MIN_LENGTH) @Max(PasswordLen.MAX_LENGTH) String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
