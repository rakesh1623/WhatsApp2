package com.example.whatsapp;

public class MessageModel {

    String userId , message , messageId;
    Long timmeStamp;


    public MessageModel(){}
    public MessageModel(String userId, String message, Long timmeStamp) {
        this.userId = userId;
        this.message = message;
        this.timmeStamp = timmeStamp;
    }

    public MessageModel(String userId, String message) {
        this.userId = userId;
        this.message = message;
    }




    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Long getTimmeStamp() {
        return timmeStamp;
    }

    public void setTimmeStamp(Long timmeStamp) {
        this.timmeStamp = timmeStamp;
    }
}
