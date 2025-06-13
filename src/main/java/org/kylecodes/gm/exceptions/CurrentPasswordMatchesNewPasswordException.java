package org.kylecodes.gm.exceptions;

import org.kylecodes.gm.constants.PasswordErrorMsg;

public class CurrentPasswordMatchesNewPasswordException extends RuntimeException{
    public CurrentPasswordMatchesNewPasswordException() {
        super(PasswordErrorMsg.CURRENT_PASSWORD_AND_NEW_PASSWORD_MUST_NOT_MATCH);
    }
}
