package com.github.anhtom2000.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * 数据收集配置，主要作用在于Spring启动时自动加载一个ExecutorService对象.
 * @author weleness
 * @date 2020/11/24
 *
 */
@SpringBootConfiguration
public class ThreadPoolConfig {

    @Bean("threadPool")
    public ExecutorService getThreadPool(){
        return Executors.newFixedThreadPool(50);
    }
}
