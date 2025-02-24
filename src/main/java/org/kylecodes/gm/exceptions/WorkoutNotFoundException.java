package org.kylecodes.gm.exceptions;

public class WorkoutNotFoundException extends RuntimeException {

    private static final long serializationId = 1;  // look this up
    private static final String DEFAULT_NOT_FOUND_MSG = "No workout found with given id.";
    public WorkoutNotFoundException(String message) {
        super(message + DEFAULT_NOT_FOUND_MSG); // pass string to parent (RuntimeException class)
    }

    public WorkoutNotFoundException(String message, Throwable cause) {
        super(message + DEFAULT_NOT_FOUND_MSG, cause);
    }

    public WorkoutNotFoundException(Throwable cause) {
        super(cause);
    }

    public WorkoutNotFoundException() {
        super(DEFAULT_NOT_FOUND_MSG);
    }
}
