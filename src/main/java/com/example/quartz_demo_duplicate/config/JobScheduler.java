package com.example.quartz_demo_duplicate.config;

import com.example.quartz_demo_duplicate.job.SimpleJob;
import jakarta.annotation.PostConstruct;
import org.quartz.*;
import org.quartz.impl.calendar.HolidayCalendar;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

@Component
public class JobScheduler {

    private final Scheduler scheduler;

    @Autowired
    public JobScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostConstruct
    public void init() throws Exception {
        System.out.println("⚠️ Bắt đầu xóa tất cả job và trigger...");

        // Xóa tất cả job hiện có
        for (String groupName : scheduler.getJobGroupNames()) {
            Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName));
            for (JobKey jobKey : jobKeys) {
                scheduler.deleteJob(jobKey);
                System.out.println("🗑️ Đã xóa job: " + jobKey);
            }
        }

        String calendarName = "holidayCalendar";
        if (scheduler.getCalendarNames().contains(calendarName)) {
            boolean deleted = scheduler.deleteCalendar(calendarName);
            if (deleted) {
                System.out.println("🗑️ Đã xóa calendar: " + calendarName);
            } else {
                System.out.println("❌ Không thể xóa calendar: " + calendarName);
            }
        } else {
            System.out.println("ℹ️ Không tìm thấy calendar để xóa.");
        }


        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_YEAR, 1);
        tomorrow.set(Calendar.HOUR_OF_DAY, 0);
        tomorrow.set(Calendar.MINUTE, 0);
        tomorrow.set(Calendar.SECOND, 0);
        tomorrow.set(Calendar.MILLISECOND, 0);
        Date startAt = tomorrow.getTime();


        if (!scheduler.getCalendarNames().contains(calendarName)) {
            HolidayCalendar holidays = new HolidayCalendar();
            holidays.addExcludedDate(startAt);

            scheduler.addCalendar(calendarName, holidays, false, false);
            System.out.println("📅 Đã thêm holidayCalendar loại trừ: " + startAt);
        } else {
            System.out.println("⚠️ holidayCalendar đã tồn tại, bỏ qua.");
        }


        JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("immediateJob", "calendarGroup")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("immediateTrigger", "calendarGroup")
                .startNow()  // chạy ngay
                .modifiedByCalendar(calendarName)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withRepeatCount(0)) // chỉ chạy 1 lần duy nhất
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println("✅ Đăng ký job với calendar xong. Nếu job KHÔNG chạy, chứng tỏ hôm nay bị chặn.");
    }
}
