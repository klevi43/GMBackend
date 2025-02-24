package org.kylecodes.gm.exceptions;

import java.util.Date;

public class ErrorResponse {

    private int status;
    private String message;
    private Date timeStamp;

    public ErrorResponse() {
    }

    public ErrorResponse(int status, String message, Date timeStamp) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
