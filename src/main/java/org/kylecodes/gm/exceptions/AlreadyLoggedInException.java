package org.kylecodes.gm.exceptions;

import org.kylecodes.gm.constants.AlreadyLoggedIn;

public class AlreadyLoggedInException extends RuntimeException {


    public AlreadyLoggedInException(Throwable cause) {
        super(cause);
    }

    public AlreadyLoggedInException() {
        super(AlreadyLoggedIn.ERROR_MSG);
    }
}
