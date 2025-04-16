package org.kylecodes.gm.exceptions;

import org.kylecodes.gm.constants.NotFoundMsg;

public class SetNotFoundException extends RuntimeException {

    private static final long serializationId = 1;  // look this up
    public SetNotFoundException(String message) {
        super(message + NotFoundMsg.SET_NOT_FOUND_MSG); // pass string to parent (RuntimeException class)
    }
}
