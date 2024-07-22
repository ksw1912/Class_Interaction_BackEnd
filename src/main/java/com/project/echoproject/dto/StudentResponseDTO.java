package com.project.echoproject.dto;

import com.project.echoproject.domain.Enrollment;
import com.project.echoproject.domain.User;

import java.util.List;

public class StudentResponseDTO {
    private User user;
    private List<Enrollment> enrollments;

    public StudentResponseDTO(User user, List<Enrollment> enrollments) {
        this.user = user;
        this.enrollments = enrollments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }
}
