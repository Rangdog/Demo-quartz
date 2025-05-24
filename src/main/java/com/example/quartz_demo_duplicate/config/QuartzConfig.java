package com.example.quartz_demo_duplicate.config;

import com.example.quartz_demo_duplicate.job.SimpleJob;
import jakarta.annotation.PostConstruct;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

@Configuration
public class QuartzConfig {

    private final DataSource dataSource;
    private final Scheduler scheduler;

    @Autowired
    public QuartzConfig(DataSource dataSource, Scheduler scheduler) {
        this.dataSource = dataSource;
        this.scheduler = scheduler;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setSchedulerName("DemoScheduler");
        factory.setStartupDelay(2);
        factory.setAutoStartup(true);
        return factory;
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
                        .withIntervalInSeconds(60)
                        .repeatForever())
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println("📌 Đăng ký job + trigger thành công.");
    }
}
