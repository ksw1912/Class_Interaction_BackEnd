package com.project.echoproject.controller;

import com.project.echoproject.domain.Enrollment;
import com.project.echoproject.jwt.JWTUtil;
import com.project.echoproject.repository.EnrollmentRepository;
import com.project.echoproject.service.EnrollmentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequestMapping("/student")
public class StudentController {
    EnrollmentService enrollmentService;
    JWTUtil jwtUtil;

    public StudentController(EnrollmentService enrollmentService, JWTUtil jwtUtil) {
        this.enrollmentService = enrollmentService;
        this.jwtUtil = jwtUtil;
    }


}
