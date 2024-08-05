package com.project.echoproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {

        corsRegistry.addMapping("/**")
                .allowedOrigins("*");

        //                .allowedMethods("*")
        //                .allowedHeaders("*")
        //                .exposedHeaders("Authorization")
        //                .allowCredentials(true)
        //                .maxAge(3600); //프론트 주소 허용
    }
}
