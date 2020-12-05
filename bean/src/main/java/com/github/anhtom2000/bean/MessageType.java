package com.github.anhtom2000.bean;

public enum MessageType {
    TYPE_CLIENT_LOGIN("login"),
    TYPE_CLIENT_REGISTER("register"),
    TYPE_CLIENT_SEND("send"),
    TYPE_CLIENT_GROUP("group"),
    TYPE_CLIENT_RECEIVE_SINGLE("receive_single"),
    TYPE_CLIENT_RECEIVE_GROUP("receive_group"),
    UNKNOWN("unknown");
    private String action;

    MessageType(String action) {
        this.action = action;
    }

    public final String getAction() {
        return action;
    }

    @Override
    public String toString() {
        return "MessageType{" +
                "action='" + action + '\'' +
                '}';
    }



    public static MessageType getActionType(String action) {
        for (MessageType messageType : MessageType.values()) {
            if (messageType.getAction().equals(action)) {
                return messageType;
            }
        }
        return UNKNOWN;
    }
}
