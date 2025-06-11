package com.example.quartz_demo_duplicate.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Trigger;

import java.net.InetAddress;
import java.time.LocalDateTime;

public class SimpleJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        try {
            String hostname = InetAddress.getLocalHost().getHostName();

            // Lấy JobKey
            JobKey jobKey = context.getJobDetail().getKey();
            // Lấy Trigger
            Trigger trigger = context.getTrigger();

            System.out.println("✅ Job executed at " + LocalDateTime.now() +
                    " on instance: " + hostname +
                    " | JobKey: " + jobKey +
                    " | TriggerKey: " + trigger.getKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
