package com.example.androidsandbox.util;

import android.app.Application;

public class JournalUser extends Application {

    private String userName;
    private String userId;

    private static JournalUser instance;

    public static JournalUser getInstance(){
        if(instance == null) {
            instance = new JournalUser();
        } return instance;
    }

    public JournalUser() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
