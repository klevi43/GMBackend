package org.kylecodes.gm.constants;

public final class EmailErrorMsg {
    private EmailErrorMsg() {
    }

    public static final String INVALID_EMAIL = "Provided email does not match the registered email";
    public static final String NEW_EMAIL_MUST_NOT_MATCH_OLD_EMAIL = "Your new email must be different from your current email.";
}
