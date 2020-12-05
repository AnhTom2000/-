package com.github.anhtom2000.server;



import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;


@SpringBootApplication(scanBasePackages = {"com.github.anhtom2000.bean","com.github.anhtom2000.config","com.github.anhtom2000.dao","com.github.anhtom2000.service","com.github.anhtom2000.server"})
@MapperScan(basePackages = "com.github.anhtom2000.dao",basePackageClasses = Repository.class)
public class ServerApplication {




    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);  System.out.println("启动服务器");

//        try {
//           ManagerServer.getInstance().start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
