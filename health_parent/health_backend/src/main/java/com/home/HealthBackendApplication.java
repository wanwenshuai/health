package com.home;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDubboConfiguration // dubbo扫描
public class HealthBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthBackendApplication.class, args);
    }

}
