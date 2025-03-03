package org.kylecodes.gm.exceptions;

public class SetNotFoundException extends RuntimeException {

    private static final long serializationId = 1;  // look this up
    private static final String DEFAULT_NOT_FOUND_MSG = "No set found with given id.";
    public SetNotFoundException(String message) {
        super(message + DEFAULT_NOT_FOUND_MSG); // pass string to parent (RuntimeException class)
    }

    public SetNotFoundException(String message, Throwable cause) {
        super(message + DEFAULT_NOT_FOUND_MSG, cause);
    }

    public SetNotFoundException(Throwable cause) {
        super(cause);
    }

    public SetNotFoundException() {
        super(DEFAULT_NOT_FOUND_MSG);
    }
}
