package com.example.quartz_demo_duplicate.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class MyJobListener implements JobListener {
    @Override
    public String getName() {
        return "job-listener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        System.out.println("➡️ Job to be executed: " + context.getJobDetail().getKey());
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException e) {
        System.out.println("✅ Job executed: " + context.getJobDetail().getKey());
    }
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {}
}
