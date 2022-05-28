package com.home;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @date 2022/5/17 17:05
 */
@SpringBootApplication
@EnableAsync // 开启异步任务
@EnableScheduling // 开启定时任务
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
