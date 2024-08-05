package com.project.echoproject.repository;

import com.project.echoproject.domain.Classroom;
import com.project.echoproject.domain.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
public interface InstructorRepository extends JpaRepository<Instructor, UUID> {
    Instructor findByEmail(String email);
}
