package org.kylecodes.gm.exceptions;

public class ExerciseNotFoundException extends RuntimeException {
    private static final long serializationId = 1;  // look this up
    private static final String DEFAULT_NOT_FOUND_MSG = "No exercise found with given id.";
    public ExerciseNotFoundException(String message) {
        super(message + DEFAULT_NOT_FOUND_MSG); // pass string to parent (RuntimeException class)
    }

    public ExerciseNotFoundException(String message, Throwable cause) {
        super(message + DEFAULT_NOT_FOUND_MSG, cause);
    }

    public ExerciseNotFoundException(Throwable cause) {
        super(cause);
    }

    public ExerciseNotFoundException() {
        super(DEFAULT_NOT_FOUND_MSG);
    }
}
