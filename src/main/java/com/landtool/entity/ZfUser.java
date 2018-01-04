package com.landtool.entity;


public class ZfUser {


  public long getUserId() {
    return userId;
  }

  public String getUser() {
    return user;
  }

  public String getPassword() {
    return password;
  }

  public String getPasswords() {
    return passwords;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setPasswords(String passwords) {
    this.passwords = passwords;
  }

  private long userId;
  private String user;
  private String password;
  private String passwords;


}
