package com.example.androidsandbox.model;


import com.google.firebase.Timestamp;

public class Journal {
    private String journalTitle;
    private String journalText;
    private String journalImage;
    private String thoughts,userId,userName;
    private Timestamp timeAdded;

    public Journal() {

    }

    public Journal(String journalTitle, String journalText, String thoughts, String journalImage, String userId, String userName) {
        this.journalTitle = journalTitle;
        this.journalText = journalText;
        this.thoughts = thoughts;
        this.journalImage = journalImage;
        this.userId = userId;
        this.userName = userName;
    }

    public String getJournalText() {
        return journalText;
    }

    public void setJournalText(String journalText) {
        this.journalText = journalText;
    }

    public String getJournalTitle() {
        return journalTitle;
    }

    public void setJournalTitle(String journalTitle) {
        this.journalTitle = journalTitle;
    }

    public String getThoughts() {
        return thoughts;
    }

    public void setThoughts(String thoughts) {
        this.thoughts = thoughts;
    }

    public String getJournalImage() {
        return journalImage;
    }

    public void setJournalImage(String journalImage) {
        this.journalImage = journalImage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Timestamp getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(Timestamp timeAdded) {
        this.timeAdded = timeAdded;
    }
}
