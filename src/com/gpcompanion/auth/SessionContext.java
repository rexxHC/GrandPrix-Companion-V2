package com.gpcompanion.auth;

public class SessionContext {
    private UserAccount current;
    public void set(UserAccount u) { current = u; }
    public boolean isLoggedIn() { return current != null; }
    public void clear() { current = null; }
}
