package com.project.echoproject.repository;

import com.project.echoproject.domain.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, UUID> {
    Optional<Classroom> findByClassName(String className);
    //classname 중복여부 판단
    boolean existsByClassName(String className);
}
