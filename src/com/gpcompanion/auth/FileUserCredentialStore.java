package com.gpcompanion.auth;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FileUserCredentialStore implements UserCredentialStore {
  private final String filePath;
  private final Map<String, UserAccount> cache;

  public FileUserCredentialStore(String filePath) {
    this.filePath = filePath;
    this.cache = new HashMap<>();
    loadAll();
  }

  public void loadAll() {
    File file = new File(filePath);
    if (!file.exists()) {
      System.out.println("no such file exists --FileUserCredentialStore");
      return;
    }
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        UserAccount account = new UserAccount(parts[0], parts[1], parts[2]);
        cache.put(account.getUsername(), account);
      }
    } catch (IOException e) {
      throw new RuntimeException("failed to load user credentials --FileUserCredentialStore", e);
    }
  }

  public void saveAll() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      for (UserAccount account : cache.values()) {
        String line = String.join(",", account.getUsername(), account.getPasswordHashed(), account.getSalt());
        writer.write(line);
        writer.newLine();
      }
    } catch (IOException e) {
      throw new RuntimeException("failed to save user credentials --FileUserCredentialStore", e);
    }
  }

  @Override
  public boolean exists(String username) {
    return cache.containsKey(username);
  }

  @Override
  public Optional<UserAccount> findByUsername(String username) {
    return Optional.ofNullable(cache.get(username));
  }

  @Override
  public void saveUserAccount(UserAccount account) {
    cache.put(account.getUsername(), account);
    saveAll();
  }
}