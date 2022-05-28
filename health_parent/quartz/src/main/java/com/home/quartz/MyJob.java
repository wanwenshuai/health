package com.home.quartz;

import org.quartz.*;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @date 2022/5/17 10:23
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class MyJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDetailMap = context.getJobDetail().getJobDataMap();
        JobDataMap triggerDetailMap = context.getTrigger().getJobDataMap();
        JobDataMap mergedMap = context.getMergedJobDataMap(); // 合并到一起

        System.out.println("jobDetailMap::"+jobDetailMap.getString("job"));
        System.out.println("triggerDetailMap::"+triggerDetailMap.getString("job"));
        System.out.println("mergedMap::"+mergedMap.getString("job"));

        triggerDetailMap.put("count",triggerDetailMap.getInt("count")+1);
        System.out.println("定时任务执行了============" + triggerDetailMap.getInt("count"));

        jobDetailMap.put("count1",jobDetailMap.getInt("count1")+1);
        System.out.println("jobDetailMap定时任务执行了======" + jobDetailMap.getInt("count1"));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
