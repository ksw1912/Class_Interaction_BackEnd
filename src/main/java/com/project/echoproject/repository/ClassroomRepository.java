package com.project.echoproject.repository;

import com.project.echoproject.domain.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, UUID> {
    Optional<Classroom> findByClassName(String className);

    //classname 중복여부 판단
    boolean existsByClassName(String className);

    boolean existsByClassId(UUID classId);

    // 특정 교수의 이메일로 클래스룸 찾기
    List<Classroom> findByInstructorEmail(String email);
}
