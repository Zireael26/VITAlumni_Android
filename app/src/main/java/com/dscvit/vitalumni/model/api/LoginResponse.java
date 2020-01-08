package com.dscvit.vitalumni.model.api;

public class LoginResponse {

    private String responseCode;
    private String name;
    private boolean isRegistered;
    private String token;

    public LoginResponse() {

    }

    public LoginResponse(String responseCode, String name, boolean isRegistered, String token) {
        this.responseCode = responseCode;
        this.name = name;
        this.isRegistered = isRegistered;
        this.token = token;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
