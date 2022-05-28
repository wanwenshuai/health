package com.home.quartz;

import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @date 2022/5/17 10:25
 */
public class TestJob  {
    public static void main(String[] args) throws SchedulerException {
        //创建一个scheduler
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        int count = 0;
        //创建一个job
        JobDetail job = JobBuilder.newJob(MyJob.class)
                .withIdentity("myjob", "mygroup")
                .usingJobData("job","JobDetail")
                .usingJobData("count1",0)
                .build();

        //创建一个Trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .usingJobData("job","JobDetail")
                .usingJobData("count",count)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1)
                        .repeatForever()).build();

        //注册trigger并启动scheduler
        scheduler.scheduleJob(job,trigger);
        scheduler.start();
    }

}
