package org.kylecodes.gm.responses;

public class LoginResponse {
    private String userEmail;
    private String userRole;
    private boolean isAuthenticated;

    public LoginResponse(String userEmail, String userRole, boolean isAuthenticated) {
        this.userEmail = userEmail;
        this.userRole = userRole;
        this.isAuthenticated = isAuthenticated;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }
}
