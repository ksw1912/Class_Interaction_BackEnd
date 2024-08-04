package com.project.echoproject.config;

import com.project.echoproject.repository.ClassroomRepository;
import com.project.echoproject.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class SpringConfig {
    @Bean
    public ConcurrentHashMap<String, String> concurrentHashMap() {
        return new ConcurrentHashMap<>();
    }
    @Bean
    public ScheduledExecutorService scheduledExecutorService() {
        return Executors.newScheduledThreadPool(1);
    }
}
