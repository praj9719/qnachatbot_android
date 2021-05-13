package com.pentagon.rcbot.object;

public class Message {

    private String message;
    private Boolean isFromUser;
    private String detailed;

    public Message(String message, String detailed, Boolean isFromUser) {
        this.message = message;
        this.detailed = detailed;
        this.isFromUser = isFromUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetailed() {
        return detailed;
    }

    public void setDetailed(String detailed) {
        this.detailed = detailed;
    }

    public Boolean getIsFromUser() {
        return isFromUser;
    }

    public void setIsFromUser(Boolean fromUser) {
        isFromUser = fromUser;
    }
}
