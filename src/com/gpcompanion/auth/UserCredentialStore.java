package com.gpcompanion.auth;

import java.util.Optional;

public interface UserCredentialStore {
    boolean exists(String username);
    Optional<UserAccount> findByUsername(String username);
    void saveUserAccount(UserAccount account);
}