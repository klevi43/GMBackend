package org.kylecodes.gm.exceptions;

import org.kylecodes.gm.constants.AdminErrorMsg;

public class AlreadyUserException extends RuntimeException{
    public AlreadyUserException() {
        super(AdminErrorMsg.IS_ALREADY_USER_MSG);
    }
}
