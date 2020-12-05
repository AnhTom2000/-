package com.github.anhtom2000.service.impl;

import com.github.anhtom2000.bean.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.anhtom2000.service.GsonService;
import com.github.anhtom2000.service.RoomService;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@Service("roomService")
public class RoomServiceImpl implements RoomService {
    @Autowired
    private ExecutorService executorService;
    @Autowired
    private GsonService gsonService;
    // 输出流，用来输出数据
    private OutputStream out;
    // 输入流，用来接收数据
    private InputStream in;
    // 每一个群聊的群成员
    private static final Map<String, List<String>> roomMember = new HashMap<>();
    // 每一个连接的线程集合
    private List<Socket> roomList = new ArrayList<>();

    @Override
    public void CreateANewRoom(Socket socket) {
        // 连接不存在，添加连接
        if (!roomList.contains(socket)) roomList.add(socket);
        // 创建线程
//        Runnable r = () -> {
            try {
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                while (true) {
                    byte[] data = new byte[1024];
                    in.read(data);
                    Message analysisMessage = gsonService.analysisString(new String(data));
//                    String type = analysisInterType.getType();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (in!=null){
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }if(out !=null){
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
//        };
//        executorService.execute(r);

    }
}
