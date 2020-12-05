package com.github.anhtom2000.config;

import com.google.gson.Gson;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class GsonConfiguration {

    @Bean
    public Gson getGson(){
        return new Gson();
    }
}
