package com.project.echoproject.dto.classroom;

import com.project.echoproject.domain.Quiz;

import java.util.List;
import java.util.UUID;

public class QuizDTO {
    UUID classId;
    List<Quiz> quiz;
    String question;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public UUID getClassId() {
        return classId;
    }

    public void setClassId(UUID classId) {
        this.classId = classId;
    }

    public List<Quiz> getQuiz() {
        return quiz;
    }

    public void setQuiz(List<Quiz> quiz) {
        this.quiz = quiz;
    }
}
