package org.kylecodes.gm.exceptions;

import org.kylecodes.gm.constants.TokenGenerationFailure;

public class TokenGenerationFailureException extends RuntimeException{

    public TokenGenerationFailureException(Throwable cause) {
        super(cause);
    }

    public TokenGenerationFailureException() {
        super(TokenGenerationFailure.ERROR_MSG);
    }
}
