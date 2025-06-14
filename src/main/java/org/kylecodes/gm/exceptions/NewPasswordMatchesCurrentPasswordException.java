package org.kylecodes.gm.exceptions;

import org.kylecodes.gm.constants.PasswordErrorMsg;

public class NewPasswordMatchesCurrentPasswordException extends RuntimeException{
    public NewPasswordMatchesCurrentPasswordException() {
        super(PasswordErrorMsg.CURRENT_PASSWORD_AND_NEW_PASSWORD_MUST_NOT_MATCH);
    }
}
