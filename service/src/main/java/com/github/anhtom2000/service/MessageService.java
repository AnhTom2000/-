package com.github.anhtom2000.service;



import com.github.anhtom2000.bean.Message;
import com.github.anhtom2000.bean.Response;

import java.nio.ByteBuffer;

public interface MessageService {



    public Message decode(String msg);

    public Response doService(Message message);

    public ByteBuffer encode(Response response);
}
