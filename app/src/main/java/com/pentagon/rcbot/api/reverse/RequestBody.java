package com.pentagon.rcbot.api.reverse;

import com.google.gson.annotations.Expose;

public class RequestBody {
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
