//package com.example.quartz_demo_duplicate.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.scheduling.quartz.SchedulerFactoryBean;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class QuartzConfig {
//
//    private final DataSource dataSource;
//
//    public QuartzConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//
//    @Bean
//    public SchedulerFactoryBean schedulerFactoryBean() {
//        SchedulerFactoryBean factory = new SchedulerFactoryBean();
//        factory.setDataSource(dataSource);
//        factory.setSchedulerName("DemoScheduler");
//        factory.setStartupDelay(2);
//        factory.setAutoStartup(true);
//        // Đọc cấu hình quartz.properties
//        factory.setConfigLocation(new ClassPathResource("quartz.properties"));
//        return factory;
//    }
//}
