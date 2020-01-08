package com.dscvit.vitalumni.model;

public class NotifModel {

    String title;
    String message;
    String date;
    String month;
    String url;

    public NotifModel() {

    }

    public NotifModel(String title, String message, String date, String month, String url) {
        this.title = title;
        this.message = message;
        this.date = date;
        this.month = month;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
