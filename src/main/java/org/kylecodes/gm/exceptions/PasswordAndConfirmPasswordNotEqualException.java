package org.kylecodes.gm.exceptions;

import org.kylecodes.gm.constants.PasswordAndConfirmPasswordNotEqual;

public class PasswordAndConfirmPasswordNotEqualException extends RuntimeException {
    public PasswordAndConfirmPasswordNotEqualException() {
        super(PasswordAndConfirmPasswordNotEqual.ERROR_MSG);
    }
}
