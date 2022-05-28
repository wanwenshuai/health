package com.home.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @date 2022/5/18 21:21
 */
@Service
public class SchedulerService {

    @Scheduled(cron = "2 * * * * ? ")
    public void sayHello(){
        System.out.println("Hello" + new Date());
    }
}
