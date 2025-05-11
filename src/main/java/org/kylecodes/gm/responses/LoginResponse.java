package org.kylecodes.gm.responses;

public class LoginResponse {
    private String userEmail;

    public LoginResponse(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
