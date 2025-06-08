package org.kylecodes.gm.exceptions;

import org.kylecodes.gm.constants.AdminErrorMsg;

public class AlreadyAdminException extends RuntimeException {
    public AlreadyAdminException() {
        super(AdminErrorMsg.IS_ALREADY_ADMIN_MSG);
    }
}
