package com.gpcompanion.auth;

public interface UserCredentialStore {
    boolean exists(String username);
    UserAccount findByUsername(String username);
    void saveUserAccount(UserAccount account);
}
