package com.project.echoproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.project.echoproject.repository")
public class EchoProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(EchoProjectApplication.class, args);
    }

}
