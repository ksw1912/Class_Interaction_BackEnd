package com.project.echoproject.service;

import com.project.echoproject.domain.Quiz;
import com.project.echoproject.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuizService {
    private QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<Quiz> quizSave(List<Quiz> quizList) {
        List<Quiz> lists = quizList.stream()
                .filter(q -> q.getClassroom() != null && q.getQuestion() != null)
                .map(q -> quizRepository.save(q))
                .collect(Collectors.toList());
        return lists;
    }
}
