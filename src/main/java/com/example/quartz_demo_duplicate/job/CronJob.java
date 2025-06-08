package com.example.quartz_demo_duplicate.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class CronJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        System.out.println("CronJob is executing at " + java.time.LocalTime.now());
    }
}
