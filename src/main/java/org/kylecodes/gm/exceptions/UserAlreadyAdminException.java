package org.kylecodes.gm.exceptions;

import org.kylecodes.gm.constants.AdminErrorMsg;

public class UserAlreadyAdminException extends RuntimeException {
    public UserAlreadyAdminException() {
        super(AdminErrorMsg.IS_ALREADY_ADMIN_MSG);
    }
}
