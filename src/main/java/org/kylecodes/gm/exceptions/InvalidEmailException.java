package org.kylecodes.gm.exceptions;

import org.kylecodes.gm.constants.EmailErrorMsg;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException() {
        super(EmailErrorMsg.INVALID_EMAIL);
    }
}
