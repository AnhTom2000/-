package com.github.anhtom2000.bean;


public class User {
    String name;
    String password;
    boolean isOnline;

    public User(String name, String password, boolean isOnline) {
        this.name = name;
        this.password = password;
        this.isOnline = isOnline;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", isOnline=" + isOnline +
                '}';
    }
}
