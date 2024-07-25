package com.project.echoproject.service;

import com.project.echoproject.domain.Quiz;
import com.project.echoproject.repository.QuizRepository;

import java.util.List;
import java.util.UUID;

public class QuizService {
    private QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    Quiz quizSave(Quiz quiz){
        return quizRepository.save(quiz);
    }

    List<Quiz> findByQuizId(UUID quizId){
        return quizRepository.findByQuizId(quizId);
    }

}
