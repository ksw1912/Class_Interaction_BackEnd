package com.project.echoproject.repository;

import com.project.echoproject.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
public interface StudentRepository extends JpaRepository<Student, UUID> {
    Student findByEmail(String email);
}
