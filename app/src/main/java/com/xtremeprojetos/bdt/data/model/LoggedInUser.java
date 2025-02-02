package com.xtremeprojetos.bdt.data.model;

public class LoggedInUser {

    private String userId;
    private String displayName;

    // Construtor
    public LoggedInUser(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    // Métodos de acesso (Getters)
    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }
}
