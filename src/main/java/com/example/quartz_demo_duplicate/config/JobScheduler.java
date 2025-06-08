package com.example.quartz_demo_duplicate.config;

import com.example.quartz_demo_duplicate.job.CronJob;
import com.example.quartz_demo_duplicate.job.SimpleJob;
import com.example.quartz_demo_duplicate.listener.MyJobListener;
import com.example.quartz_demo_duplicate.listener.MySchedulerListener;
import com.example.quartz_demo_duplicate.listener.MyTriggerListener;
import jakarta.annotation.PostConstruct;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobScheduler {

    private final Scheduler scheduler;

    @Autowired
    public JobScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostConstruct
    public void init() throws Exception {
        // Đăng ký các listener thủ công như trong QuartzMain
        scheduler.getListenerManager().addJobListener(new MyJobListener());
        scheduler.getListenerManager().addTriggerListener(new MyTriggerListener());
        scheduler.getListenerManager().addSchedulerListener(new MySchedulerListener());

        // Tạo SimpleJob
        JobDetail simpleJob = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("simpleJob", "group1")
                .build();

        Trigger simpleTrigger = TriggerBuilder.newTrigger()
                .withIdentity("simpleTrigger", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10)
                        .withRepeatCount(2))
                .build();

        // Tạo CronJob
        JobDetail cronJob = JobBuilder.newJob(CronJob.class)
                .withIdentity("cronJob", "group2")
                .build();

        Trigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("cronTrigger", "group2")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/15 * * * * ?"))
                .build();

        // Đăng ký với scheduler
        scheduler.scheduleJob(simpleJob, simpleTrigger);
        scheduler.scheduleJob(cronJob, cronTrigger);

        // Bắt đầu scheduler (cần nếu chưa start tự động)
        scheduler.start();

        System.out.println("✅ Jobs & triggers scheduled in Spring Boot context.");
    }
}
