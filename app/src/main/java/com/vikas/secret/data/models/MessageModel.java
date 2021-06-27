package com.vikas.secret.data.models;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;

public class MessageModel {

    private String uId;
    private String message;
    private Long timestamp;

    public MessageModel(String uId, String message) {
        this.uId = uId;
        this.message = message;
        this.timestamp = new Date().getTime();
    }

    public MessageModel(String uId, String message, Long timestamp) {
        this.uId = uId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getTime() {
        Timestamp timestamp = new Timestamp(this.timestamp);
        Date date = new Date(timestamp.getTime());
        return DateFormat.getDateInstance().format(date);
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
