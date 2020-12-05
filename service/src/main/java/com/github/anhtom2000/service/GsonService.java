package com.github.anhtom2000.service;

import com.github.anhtom2000.bean.Message;
import com.github.anhtom2000.bean.Response;

public interface GsonService {

    public Message analysisString(String message);

    public String toJson(Response response);


}
