package com.project.echoproject.dto;

import com.project.echoproject.domain.Classroom;
import com.project.echoproject.domain.User;

import java.util.List;

public class InstructorResponseDTO {
    private User user;
    private List<Classroom> classrooms;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Classroom> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(List<Classroom> classrooms) {
        this.classrooms = classrooms;
    }
}
