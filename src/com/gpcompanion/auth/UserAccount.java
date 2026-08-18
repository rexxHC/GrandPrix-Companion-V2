package com.gpcompanion.auth;

public class UserAccount {
  private final String username;
  private final String passwordHashed;
  private final String salt;

  public UserAccount(String username, String passwordHashed, String salt) {
    this.username = username;
    this.passwordHashed = passwordHashed;
    this.salt = salt;
  }

  public String getUsername() {
    return this.username;
  }

  public String getPasswordHashed() {
    return this.passwordHashed;
  }

  public String getSalt() {
    return this.salt;
  }
}