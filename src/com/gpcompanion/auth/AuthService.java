package com.gpcompanion.auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;


public class AuthService {
  private final UserCredentialStore store;
  private final SessionContext session;
  private static final String LOGIN_FAILURE = "Invalid username or password...";

  public AuthService(UserCredentialStore store, SessionContext session) {
    this.store = store;
    this.session = session;
  }

  private void validateCredentials(String username, String password) {
    if (username == null || password == null) {
        throw new IllegalArgumentException("username and password must not be null");
    }
    if (username.isEmpty() || password.isEmpty()) {
        throw new IllegalArgumentException("username and password must not be empty");
    }
    if (username.contains(" ") || username.contains("\n")
            || password.contains(" ") || password.contains("\n")) {
        throw new IllegalArgumentException("username and password must not contain spaces or newlines");
    }
  }

  private String generateSalt() {
    SecureRandom random = new SecureRandom();
    byte[] saltBytes = new byte[16];
    random.nextBytes(saltBytes);
    StringBuilder hex = new StringBuilder();
    for (byte b : saltBytes) {
      hex.append(String.format("%02x", b));
    }
    return hex.toString();
  }

  // SHA-256 hash function, salted
  private String hashPassword(String password, String salt) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hashBytes = digest.digest((salt + password).getBytes());
      StringBuilder hex = new StringBuilder();
      for (byte b : hashBytes) {
        hex.append(String.format("%02x", b));
      }
      return hex.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("SHA-256 not available", e);
    }
  }

  public void register(String username, String password) throws DuplicateUserException {
    validateCredentials(username, password);
    if (this.store.exists(username)) {
      throw new DuplicateUserException("Username already taken " + username);
    }
    String salt = generateSalt();
    UserAccount user = new UserAccount(username, hashPassword(password, salt), salt);
    store.saveUserAccount(user);
  }

  public void login(String username, String password) throws AuthenticationException {
    validateCredentials(username, password);
    Optional<UserAccount> optionalAcc = store.findByUsername(username);
    UserAccount account;
    if (optionalAcc.isPresent()) {
      account = optionalAcc.get();
    } else {
      throw new AuthenticationException(LOGIN_FAILURE);
    }

    if (hashPassword(password, account.getSalt()).equals(account.getPasswordHashed())) {
      session.setCurrentUser(account);
    } else {
      throw new AuthenticationException(LOGIN_FAILURE);
    }
  }
}