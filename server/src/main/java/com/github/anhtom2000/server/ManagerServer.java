package com.github.anhtom2000.server;


import com.github.anhtom2000.bean.Message;
import com.github.anhtom2000.bean.ReceiveResponse;
import com.github.anhtom2000.bean.Response;
import com.github.anhtom2000.bean.User;
import com.github.anhtom2000.service.MessageService;
import com.github.anhtom2000.service.RoomService;
import com.github.anhtom2000.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.anhtom2000.bean.MessageType.*;
import static com.github.anhtom2000.bean.ResponseType.*;

// 加入到容器中，可以直接调用该类
@Component
public class ManagerServer {

    @Qualifier("roomService")
    @Autowired
    private RoomService roomService;

    @Qualifier("messageService")
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);//调整缓存的大小可以看到打印输出的变化
    private ByteBuffer sendBuffer = ByteBuffer.allocate(1024);//调整缓存的大小可以看到打印输出的变化
    // 固定端口监听
    private final int PORT = 9999;
    private String str;
    private boolean running = true;

    /**
     * 构造方法，初始化服务器
     *
     * @throws IOException
     */
    private ManagerServer() {
    }

    @PostConstruct
    public void start() throws IOException {
        try {
            // 创建选择器
            selector = Selector.open();
            // 打开监听信道
            serverSocketChannel = ServerSocketChannel.open();
            InetSocketAddress adress = new InetSocketAddress("192.168.35.218", PORT);
            //与本地端口绑定
            serverSocketChannel.socket().bind(adress);
            // 设置为非阻塞模式
            serverSocketChannel.configureBlocking(false);
            // 注册选择器.并在注册过程中指出该信道可以进行Accept操作
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (running) {
                int eventCount = selector.select(100);
                if (eventCount == 0)
                    continue;
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = keys.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    keyIterator.remove(); //该事件已经处理，可以丢弃
                    dealEvent(key);
                }
            }
        } finally {
            if (selector != null && selector.isOpen())
                selector.close();
            if (serverSocketChannel != null && serverSocketChannel.isOpen())
                serverSocketChannel.close();
        }
    }

    private void dealEvent(SelectionKey key) throws IOException {
        if (key.isAcceptable()) {
            accept(key);
        }
        if (key.isReadable()) {
            read(key);
        }
    }

    private void accept(SelectionKey key) throws IOException {
        System.out.println("客户端完成连接.");
        SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        socketChannel.write(ByteBuffer.wrap("连接成功".getBytes()));
    }

    private void write(SelectionKey key) throws IOException, ClosedChannelException {
        SocketChannel channel = (SocketChannel) key.channel();
        System.out.println("write:" + str);
        sendBuffer.put("str.getBytes()".getBytes());
        channel.write(sendBuffer);
        channel.register(selector, SelectionKey.OP_READ);
    }


    private void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = null;
        System.out.println("从客户端收取请求.");
        String msg;
        try {
            socketChannel = (SocketChannel) key.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            socketChannel.read(byteBuffer);
            byteBuffer.flip();
            msg = Message.CHARSET.decode(byteBuffer).toString();
            dealMsg(msg, key);// 处理消息和响应
        } catch (IOException e) {
            socketChannel.close();
            String username = (String) key.attachment();
            System.out.println(String.format("下线用户: %s", username));
            broadcastUserList();
        }
    }

    private void dealMsg(String msg, SelectionKey key) throws IOException {
        System.out.println(String.format("客户端发送消息内容是 : %s", msg));
        Message message = messageService.decode(msg);// 解析发过来的json字符串
        if (message == null)
            return;

        SocketChannel currentChannel = (SocketChannel) key.channel();
        Set<SelectionKey> keySet = getConnectedChannel();
        String type = message.getType();
        Response response = null;
        if (TYPE_CLIENT_LOGIN.getAction().equals(type)) {
            User user = userService.findUserByName(message.getUser().getName());
            if (user == null) {
                response = new Response(message.getType(), message.getUser(), LOGIN_ERROR.getCode(), "用户还未注册");
            } else {
                for (SelectionKey keyItem : keySet) {
                    User channelUser = (User) keyItem.attachment();
                    if (channelUser != null && channelUser.equals(user)) {
                        response = new Response(message.getType(), user, LOGIN_ERROR.getCode(), "用户已登录");
                        break;
                    }
                    response = new Response(message.getType(), user, SUCCESS.getCode(), "登陆成功");
                }
                key.attach(user);
            }
        } else if (TYPE_CLIENT_REGISTER.getAction().equals(type)) {
            
        }

        doResponse(key, response);
//        Response response = messageService.doService(message);
//        doResponse(key, response);

    }

    private void doResponse(SelectionKey key, Response response) {
        SocketChannel channel = (SocketChannel) key.channel();
        try {
            channel.write(messageService.encode(response));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doReceive(SelectionKey key, ReceiveResponse receiveResponse) {

    }


    private Set<SelectionKey> getConnectedChannel() {
        return selector.keys().stream()
                .filter(item -> item.channel() instanceof SocketChannel && item.channel().isOpen())
                .collect(Collectors.toSet());
    }

    public void broadcastUserList() throws IOException {
        Set<SelectionKey> keySet = getConnectedChannel();
        List<String> uList = keySet.stream().filter(item -> item.attachment() != null).map(SelectionKey::attachment)
                .map(Object::toString).collect(Collectors.toList());
        for (SelectionKey keyItem : keySet) {
            SocketChannel channel = (SocketChannel) keyItem.channel();
            channel.write(Message.encodePublishUserList(uList));
        }
    }
}


