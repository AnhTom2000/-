package com.github.anhtom2000.bean;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class Message {
    public static final Charset CHARSET = StandardCharsets.UTF_8;

    // 请求类型
    private String type;

    // 发请求的用户
    private User user;

    // 如果是发送消息，有消息
    private String content;
    // 是否是私聊
    private boolean isSingle;
    // 发送给谁
    private String to;
    // 群聊成员
    private List<User> roomMember;

    public Message() {
    }

    public Message(String type, User user, String content, boolean isSingle, String to, List<User> roomMember) {
        this.type = type;
        this.user = user;
        this.content = content;
        this.isSingle = isSingle;
        this.to = to;
        this.roomMember = roomMember;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSingle() {
        return isSingle;
    }

    public void setSingle(boolean single) {
        isSingle = single;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<User> getRoomMember() {
        return roomMember;
    }

    public void setRoomMember(List<User> roomMember) {
        this.roomMember = roomMember;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type='" + type + '\'' +
                ", user=" + user +
                ", content='" + content + '\'' +
                ", isSingle=" + isSingle +
                ", to='" + to + '\'' +
                ", roomMember=" + roomMember +
                '}';
    }
}
