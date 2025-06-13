package org.kylecodes.gm.exceptions;

import org.kylecodes.gm.constants.PasswordErrorMsg;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException() {
        super(PasswordErrorMsg.INVALID_PASSWORD);
    }
}
