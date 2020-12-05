package com.github.anhtom2000.service.impl;

import com.github.anhtom2000.bean.Message;
import com.github.anhtom2000.bean.Response;
import com.github.anhtom2000.bean.User;
import com.github.anhtom2000.dao.MessageDao;
import com.github.anhtom2000.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.github.anhtom2000.service.GsonService;
import com.github.anhtom2000.service.MessageService;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static com.github.anhtom2000.bean.MessageType.*;
import static com.github.anhtom2000.bean.ResponseType.*;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

    @Qualifier("gsonService")
    @Autowired
    private GsonService gsonService;



    private final List<User> userList = new ArrayList<>();

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private UserDao userDao;

    @Override
    public Message decode(String message) {
        if (message == null || message.length() == 0)
            return null;
        return gsonService.analysisString(message);
    }



    @Override
    public Response doService(Message message) {
        String action = message.getType();
        Response response = null;
        if (TYPE_CLIENT_REGISTER.getAction().equals(action)) {// 注册
            User user = userDao.findUserByName(message.getUser().getName());
            if (user == null) { // 用户不存在，注册成功
                userDao.register(message.getUser());
                response = new Response(action, message.getUser(), SUCCESS.getCode(), "注册成功");
            } else {
                response = new Response(action, message.getUser(), REGISTER_FAILED.getCode(), "用户已注册");
            }
        } else if (TYPE_CLIENT_LOGIN.getAction().equals(action)) { // 登陆
            User user = userDao.findUserByName(message.getUser().getName());
            if (user == null) { // 用户不存在，登陆失败
                response = new Response(action, message.getUser(), NOTFOUND.getCode(), "用户不存在");
            } else {
                if (user.getPassword().equals(message.getUser().getPassword())) { // 密码正确,允许登陆
                    response = new Response(action, message.getUser(), SUCCESS.getCode(), "登陆成功");
                    userList.add(user); // 添加已登陆的用户
                } else { // 密码错误
                    response = new Response(action, message.getUser(), LOGIN_ERROR.getCode(), "密码错误");
                }
            }

        } else if (TYPE_CLIENT_SEND.getAction().equals(action)) { // 私聊
            messageDao.addMessage(message);
            response = new Response(action, message.getUser(), SUCCESS.getCode(), "发送成功");
        } else if (TYPE_CLIENT_GROUP.getAction().equals(action)) { // 群聊
            List<User> roomMember = message.getRoomMember();
            roomMember.forEach(user -> { // 群聊
                User u = findUser(user.getName());
                message.setTo(u.getName());
                messageDao.addMessage(message);
            });
            response = new Response(action, message.getUser(), SUCCESS.getCode(), "发送成功");
        } else if (TYPE_CLIENT_RECEIVE_SINGLE.getAction().equals(action)) {
            User user = message.getUser();
            List<Message> messages = messageDao.getMessage(user);// 获取新消息集合
            messageDao.updateMessage(user.getName());// 更新状态
        } else { // 无效的请求
            response = new Response(action, message.getUser(), SERVER_ERROR.getCode(), "无效操作");
        }
        return response;
    }

    @Override
    public ByteBuffer encode(Response response) {
        return Message.CHARSET.encode(gsonService.toJson(response));
    }


    private User findUser(String username) {
        return userList.stream().filter(user -> user.getName().equals(username)).findFirst().get();
    }
}
