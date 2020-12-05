package com.github.anhtom2000.service.impl;

import com.github.anhtom2000.bean.Message;
import com.github.anhtom2000.bean.Response;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.anhtom2000.service.GsonService;

@Service("gsonService")
class GsonServiceImpl implements GsonService {

    @Autowired
    private Gson gson;

    @Override
    public Message analysisString(String json) {
        StringBuilder result = new StringBuilder();
        for (String line: json.split("\n")) {
            result.append(line.trim());
            result.append("\n");
        }
        return gson.fromJson(result.toString(), Message.class);
    }

    @Override
    public String toJson(Response response) {
        return gson.toJson(response);
    }
}
