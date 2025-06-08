package org.kylecodes.gm.exceptions;

import org.kylecodes.gm.constants.NotFoundMsg;

public class WorkoutNotFoundException extends RuntimeException {

    private static final String DEFAULT_NOT_FOUND_MSG = "No workout found with given id.";
    public WorkoutNotFoundException(String message) {
        super(message + NotFoundMsg.WORKOUT_NOT_FOUND_MSG); // pass string to parent (RuntimeException class)
    }

}
