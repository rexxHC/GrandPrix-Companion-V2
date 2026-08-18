package com.gpcompanion.auth;

public class AuthService {
    private final UserCredentialStore store;
    public AuthService(UserCredentialStore store) { this.store = store; }
    public void register(String u, String p) throws DuplicateUserException {
        if(store.exists(u)) throw new DuplicateUserException("User exists");
        store.saveUserAccount(new UserAccount(u, p, "salt"));
    }
    public void login(String u, String p) throws AuthenticationException {
        if(!store.exists(u)) throw new AuthenticationException("Login failed");
        UserAccount account = store.findByUsername(u);
        if(!account.passwordHash.equals(p)) throw new AuthenticationException("Login failed");
    }
}
