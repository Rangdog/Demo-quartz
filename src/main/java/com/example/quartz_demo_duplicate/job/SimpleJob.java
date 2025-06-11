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
            SimpleTrigger simpleTrigger = (SimpleTrigger) trigger;

            JobDataMap dataMap = context.getJobDetail().getJobDataMap();

            // Đếm số lần thủ công (nếu cần)
            int runCount = 0;
            Object countObj = dataMap.get("runCount");
            if (countObj instanceof Integer) {
                runCount = (Integer) countObj;
            } else if (countObj instanceof String) {
                runCount = Integer.parseInt((String) countObj);
            }
            runCount++;
            dataMap.put("runCount", runCount);

            // In thông tin chi tiết
            System.out.println("✅ Job executed at " + LocalDateTime.now() +
                    "\n📍 Instance: " + hostname +
                    "\n🔑 JobKey: " + jobKey +
                    "\n🧷 TriggerKey: " + trigger.getKey() +
                    "\n🔁 Configured Repeat Count: " + simpleTrigger.getRepeatCount() +
                    "\n🔄 Times Triggered (Quartz): " + simpleTrigger.getTimesTriggered() +
                    "\n🧮 Run Count (JobDataMap): " + runCount +
                    "\n--------------------------------------------");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
