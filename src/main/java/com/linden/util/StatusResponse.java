package com.linden.util;

import java.io.Serializable;

public class StatusResponse implements Serializable {

    protected String status;
    protected String message = "";

    public StatusResponse(String status) {
        this.status = status;
    }

    public StatusResponse(String status, String message) {
        this(status);
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
