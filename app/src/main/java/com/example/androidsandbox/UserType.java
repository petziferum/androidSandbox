package com.example.androidsandbox;

import com.google.firebase.firestore.auth.User;

public class UserType {
    String displayName;
    String email;
    String firstName;
    String lastName;
    String ranking;
    String userName;
    String userId;

    public UserType(){};

    public UserType(String displayName, String email, String firstName, String lastName, String ranking, String userName, String userId) {
        this.displayName = displayName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ranking = ranking;
        this.userName = userName;
        this.userId = userId;
    }

    public UserType createEmptyUser() {
        return new UserType();
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
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
