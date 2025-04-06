package org.kylecodes.gm.exceptions;

import org.kylecodes.gm.constants.EmailAlreadyExists;

public class EmailAlreadyExistsException extends RuntimeException{

    public EmailAlreadyExistsException() {
        super(EmailAlreadyExists.ERROR_MSG);
    }
}
