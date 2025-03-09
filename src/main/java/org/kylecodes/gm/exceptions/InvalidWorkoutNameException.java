package org.kylecodes.gm.exceptions;

import org.springframework.validation.BindingResult;

public class InvalidWorkoutNameException extends RuntimeException {
    private static final String DEFAULT_MSG = "Workout name must contain between 2 and 200 characters";

    private final BindingResult bindingResult;

    public InvalidWorkoutNameException(BindingResult bindingResult) {
        super(DEFAULT_MSG);
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }
}
