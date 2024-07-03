package com.example.myapplication.Data.model.model;


public class User {
    private String mobile;
    private String mpin;

    public User(String mobile, String mpin) {
        this.mobile = mobile;
        this.mpin = mpin;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMpin() {
        return mpin;
    }

    public void setMpin(String mpin) {
        this.mpin = mpin;
    }
}
