package org.kylecodes.gm.exceptions;

import org.kylecodes.gm.constants.PasswordErrorMsg;

public class PasswordAndConfirmPasswordNotEqualException extends RuntimeException {
    public PasswordAndConfirmPasswordNotEqualException() {
        super(PasswordErrorMsg.PASSWORD_AND_CONFIRM_PASSWORD_MUST_BE_EQUAL_VALUES);
    }
}
