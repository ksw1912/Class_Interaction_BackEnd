//package com.project.echoproject.config;
//
//import com.project.echoproject.repository.ClassroomRepository;
//import com.project.echoproject.service.ClassroomService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SpringConfig {
//    private final ClassroomRepository classroomRepository;
//
//    @Autowired
//    public SpringConfig(ClassroomRepository classroomRepository) {
//        this.classroomRepository = classroomRepository;
//    }
//    @Bean
//    public ClassroomService classroomService(){
//        return new ClassroomService(classroomRepository);
//    }
//}
