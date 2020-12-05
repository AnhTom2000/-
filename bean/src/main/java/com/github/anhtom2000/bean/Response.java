package com.github.anhtom2000.bean;

import java.util.List;

public class Response {

    private String type;
    private User user;
    List<String> newMessage;
    private int code;
    private String description;

    public Response() {
    }

    public Response(String type, User user, int code, String description) {
        this.type = type;
        this.user = user;
        this.code = code;
        this.description = description;
    }

    public Response(String type, User user, List<String> newMessage, int code, String description) {
        this.type = type;
        this.user = user;
        this.newMessage = newMessage;
        this.code = code;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(List<String> newMessage) {
        this.newMessage = newMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Response{" +
                "type='" + type + '\'' +
                ", user=" + user +
                ", newMessage=" + newMessage +
                ", code=" + code +
                ", description='" + description + '\'' +
                '}';
    }
}


