package com.project.echoproject.repository;

import com.project.echoproject.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QuizRepository extends JpaRepository<Quiz, UUID> {
    List<Quiz> findByQuizId(UUID quizId);
}
