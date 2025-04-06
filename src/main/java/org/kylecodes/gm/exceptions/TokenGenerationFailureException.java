package org.kylecodes.gm.exceptions;

import org.kylecodes.gm.constants.TokenGenerationFailure;

public class TokenGenerationFailureException extends RuntimeException{


    public TokenGenerationFailureException() {
        super(TokenGenerationFailure.ERROR_MSG);
    }
}
