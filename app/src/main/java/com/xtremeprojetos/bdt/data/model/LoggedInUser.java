package com.xtremeprojetos.bdt.data.model;

public class LoggedInUser {
    private final int userId;
    private final String displayName;

    public LoggedInUser(int userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public int getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }
}