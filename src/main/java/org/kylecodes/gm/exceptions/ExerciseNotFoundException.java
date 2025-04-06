package org.kylecodes.gm.exceptions;

import org.kylecodes.gm.constants.NotFoundMsg;

public class ExerciseNotFoundException extends RuntimeException {
    private static final long serializationId = 1;  // look this up

    public ExerciseNotFoundException(String message) {
        super(message + NotFoundMsg.EXERCISE_NOT_FOUND_MSG); // pass string to parent (RuntimeException class)
    }
}
