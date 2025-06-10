package org.kylecodes.gm.exceptions;

import org.kylecodes.gm.constants.NotFoundMsg;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super(NotFoundMsg.USER_NOT_FOUND_MSG);
    }
}
