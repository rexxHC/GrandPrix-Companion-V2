package com.gpcompanion.auth;

public class UserAccount {
    public final String username;
    public final String passwordHash;
    public final String salt;
    public UserAccount(String u, String p, String s) { username=u; passwordHash=p; salt=s; }
}
