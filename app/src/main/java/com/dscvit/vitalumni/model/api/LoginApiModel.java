package com.dscvit.vitalumni.model.api;

import com.google.gson.annotations.SerializedName;

public class LoginApiModel {

    @SerializedName("userName")
    String userName;

    @SerializedName("password")
    String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginApiModel(String email, String password) {
        this.userName = email;
        this.password = password;
    }
}
