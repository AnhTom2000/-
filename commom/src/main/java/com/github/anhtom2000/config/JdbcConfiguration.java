package com.github.anhtom2000.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@PropertySource("classpath:jdbc.properties")
@SpringBootConfiguration
public class JdbcConfiguration {
    @Value("${jdbc.driver}")
    private String driver;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.user}")
    private String user;
    @Value("${jdbc.password}")
    private String password;

    /**
     * @Method
     * Description:
     *  配置hikari数据源，交给spring管理，让mybatis使用这个数据源
     * @Author weleness
     *
     * @Return
     */
    @Bean("dataSource")
    public DataSource hikariDataSource(){
        HikariDataSource hd = new HikariDataSource();

        hd.setDriverClassName(driver);
        hd.setJdbcUrl(url);
        hd.setUsername(user);
        hd.setPassword(password);
        return hd;
    }

}
