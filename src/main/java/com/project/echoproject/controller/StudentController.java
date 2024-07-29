package com.project.echoproject.controller;

import com.project.echoproject.domain.Enrollment;
import com.project.echoproject.dto.ApiResponse;
import com.project.echoproject.jwt.JWTUtil;
import com.project.echoproject.repository.EnrollmentRepository;
import com.project.echoproject.service.EnrollmentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @DeleteMapping("/enrollmentDelete/{id}")
    public ApiResponse deleteEnrollment(@PathVariable UUID id) {
        enrollmentService.deleteEnrollment(id);
        return new ApiResponse("수업 삭제 성공");
    }


}
