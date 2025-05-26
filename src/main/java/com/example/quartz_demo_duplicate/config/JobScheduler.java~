package com.example.quartz_demo_duplicate.config;

import com.example.quartz_demo_duplicate.job.SimpleJob;
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
    public void scheduleJob() throws SchedulerException {
        JobKey jobKey = new JobKey("simpleJob", "group1");
        TriggerKey triggerKey = new TriggerKey("simpleJobTrigger", "group1");

        if (scheduler.checkExists(jobKey)) {
            System.out.println("✅ Job đã tồn tại, không tạo lại.");
            return;
        }

        JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class)
                .withIdentity(jobKey)
                .storeDurably()
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .forJob(jobDetail)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(30)
                        .withRepeatCount(4) // Ví dụ chạy 5 lần tất cả (0-based)
                        .withMisfireHandlingInstructionNowWithExistingCount()
                )
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println("📌 Đăng ký job + trigger thành công.");
    }
}
