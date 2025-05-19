package com.example.quartz_demo_duplicate.job;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import java.net.InetAddress;
import java.time.LocalDateTime;

public class SimpleJob  implements Job{
    @Override
    public void execute(JobExecutionContext context) {
        try {
            String hostname = InetAddress.getLocalHost().getHostName();
            System.out.println("✅ Job executed at " + LocalDateTime.now() + " on instance: " + hostname);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
