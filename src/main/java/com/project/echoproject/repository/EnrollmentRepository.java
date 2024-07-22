package com.project.echoproject.repository;

import com.project.echoproject.domain.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    List<Enrollment> findByStudentEmail(String email);
    boolean existsByClassroomClassId(UUID classId);
}
