package org.kylecodes.gm.exceptions;

import org.kylecodes.gm.constants.EmailAlreadyExists;

public class EmailAlreadyExistsException extends RuntimeException{

    public EmailAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public EmailAlreadyExistsException() {
        super(EmailAlreadyExists.MSG);
    }
}
