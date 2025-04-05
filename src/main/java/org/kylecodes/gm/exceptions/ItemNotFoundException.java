package org.kylecodes.gm.exceptions;

import org.kylecodes.gm.constants.NotFoundMsg;

public class ItemNotFoundException extends RuntimeException {

    private static final long serializationId = 1;  // look this up
    private static final String DEFAULT_NOT_FOUND_MSG = "No item found with given id.";
    public ItemNotFoundException(String message) {
        super(message + NotFoundMsg.WORKOUT_NOT_FOUND_MSG); // pass string to parent (RuntimeException class)
    }

    public ItemNotFoundException(String message, Throwable cause) {
        super(message + NotFoundMsg.WORKOUT_NOT_FOUND_MSG, cause);
    }

    public ItemNotFoundException(Throwable cause) {
        super(cause);
    }

    public ItemNotFoundException() {
        super(NotFoundMsg.WORKOUT_NOT_FOUND_MSG);
    }
}
