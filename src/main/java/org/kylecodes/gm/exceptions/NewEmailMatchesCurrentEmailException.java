package org.kylecodes.gm.exceptions;

import org.kylecodes.gm.constants.EmailErrorMsg;

public class NewEmailMatchesCurrentEmailException extends RuntimeException {
    public NewEmailMatchesCurrentEmailException() {
        super(EmailErrorMsg.NEW_EMAIL_MUST_NOT_MATCH_OLD_EMAIL);
    }
}
