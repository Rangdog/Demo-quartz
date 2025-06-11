package com.example.quartz_demo_duplicate.job;

import org.quartz.*;

import java.net.InetAddress;
import java.time.LocalDateTime;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SimpleJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        try {
            String hostname = InetAddress.getLocalHost().getHostName();
            JobKey jobKey = context.getJobDetail().getKey();
            Trigger trigger = context.getTrigger();

            JobDataMap dataMap = context.getJobDetail().getJobDataMap();

            Object countObj = dataMap.get("runCount");
            int runCount = 0;

            if (countObj instanceof Integer) {
                runCount = (Integer) countObj;
            } else if (countObj instanceof String) {
                runCount = Integer.parseInt((String) countObj);
            }

            runCount++;
            dataMap.put("runCount", runCount);

            System.out.println("✅ Job executed at " + LocalDateTime.now() +
                    " on instance: " + hostname +
                    " | JobKey: " + jobKey +
                    " | TriggerKey: " + trigger.getKey() +
                    " | Repeat Count: " + runCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
