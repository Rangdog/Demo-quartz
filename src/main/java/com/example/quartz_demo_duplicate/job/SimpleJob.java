package com.example.quartz_demo_duplicate.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;


public class SimpleJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        System.out.println("SimpleJob is executing at " + java.time.LocalTime.now());
    }
}
