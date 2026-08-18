package com.gpcompanion.auth;

public class SessionContext {
    private UserAccount currentUser;

    public void setCurrentUser(UserAccount account) {
        this.currentUser = account;
    }

    public UserAccount getCurrentUser() {
        return this.currentUser;
    }

    public boolean isLoggedIn() {
        return this.currentUser != null;
    }

    public void clear() {
        this.currentUser = null;
    }
}