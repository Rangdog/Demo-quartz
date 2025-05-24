package com.example.quartz_demo_duplicate.config;

import com.example.quartz_demo_duplicate.job.SimpleJob;
import jakarta.annotation.PostConstruct;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfig {

    private final SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    public QuartzConfig(SchedulerFactoryBean schedulerFactoryBean) {
        this.schedulerFactoryBean = schedulerFactoryBean;
    }

    @PostConstruct
    public void scheduleJob() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
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
                        .withIntervalInSeconds(60)
                        .repeatForever())
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println("📌 Đăng ký job + trigger thành công.");
    }
}