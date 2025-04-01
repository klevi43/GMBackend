package org.kylecodes.gm.exceptions;

import org.kylecodes.gm.constants.AlreadyLoggedInMsg;

public class AlreadyLoggedInException extends RuntimeException {


    public AlreadyLoggedInException(Throwable cause) {
        super(cause);
    }

    public AlreadyLoggedInException() {
        super(AlreadyLoggedInMsg.MSG);
    }
}
