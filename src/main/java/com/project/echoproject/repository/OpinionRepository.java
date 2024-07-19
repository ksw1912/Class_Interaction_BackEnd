package com.project.echoproject.repository;

import com.project.echoproject.domain.Opinion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OpinionRepository extends  JpaRepository<Opinion, UUID> {
    boolean existsByOpinion(String opinion);
    boolean existsByClassroomClassId(UUID classId);
    void deleteByClassroomClassId(UUID classId);
}
