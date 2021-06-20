package com.vikas.secret.data.models;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;

public class AngerModel {
    private String uId,reason, description;
    private Long timestamp;
    private String fromDate, toDate;

    public AngerModel(String uId, String reason, String description, String fromDate,String toDate, Long timestamp) {
        this.uId = uId;
        this.reason = reason;
        this.description = description;
        this.timestamp = timestamp;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate =  DateFormat.getDateInstance().format(fromDate);
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = DateFormat.getDateInstance().format(toDate);
    }

    public String getFormattedDateRange() {
        return fromDate + " - " + toDate ;
    }
}
