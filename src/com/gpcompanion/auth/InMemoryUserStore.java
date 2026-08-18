package com.gpcompanion.auth;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserStore implements UserCredentialStore {
    private final Map<String, UserAccount> store = new HashMap<>();
    public boolean exists(String username) { return store.containsKey(username); }
    public UserAccount findByUsername(String username) { return store.get(username); }
    public void saveUserAccount(UserAccount account) { store.put(account.username, account); }
}
