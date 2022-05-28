package com.home;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDubboConfiguration // dubbo扫描
@MapperScan(basePackages = {"com.home.dao"})
@EnableAsync // 开启异步执行
@EnableScheduling // 开启定时任务
public class HealthServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthServiceProviderApplication.class, args);
    }

}
