package com.project.echoproject.dto.websocketDTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.echoproject.domain.Opinion;
import com.project.echoproject.domain.Quiz;

import java.util.*;

public class MessageDTO {
    public enum Status {
        OPEN, CLOSE, OPINION, OPINIONINITIALIZE, OPINIONUPDATE, QUIZ, QUIZUPDATE, EVALUATION, PEOPLESTATUS,
        //open 사용자입장
        //close 사용자퇴장
        //opinion 의견제출하기
        //opinionupdate 교수 의견 업데이트
        //quiz 퀴즈풀기
        //quizupdate 교수 퀴즈 업데이트
        //evaluation 나가기전 수업평가
        //persionstatus 사용자인원

    }

    private Status status;
    private Opinion opinion;
    private Quiz quiz;
    private List<Opinion> opinionList;
    private List<Quiz> quizList;
    private int evaluation;
    private UUID classId;
    @JsonIgnore
    private boolean classStatus;
    private Set<String> userEmails;

    @JsonCreator
    public MessageDTO(Status status, Opinion opinion, Quiz quiz, List<Opinion> opinionList, List<Quiz> quizList, int evaluation, UUID classId, boolean classStatus) {
        this.status = status;
        this.opinion = opinion;
        this.quiz = quiz;
        this.opinionList = opinionList != null ? new ArrayList<>(opinionList) : null;
        this.quizList = quizList != null ? new ArrayList<>(quizList) : null;
        this.evaluation = evaluation;
        this.classId = classId;
        this.classStatus = classStatus;
        this.userEmails = new HashSet<>();
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Opinion getOpinion() {
        return opinion;
    }

    public void setOpinion(Opinion opinion) {
        this.opinion = opinion;
    }

    public List<Quiz> getQuizList() {
        return quizList;
    }

    public void setQuizList(List<Quiz> quizList) {
        this.quizList = quizList;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    public List<Opinion> getOpinionList() {
        return opinionList;
    }

    public void setOpinionList(List<Opinion> opinionList) {
        this.opinionList = opinionList;
    }

    public UUID getClassId() {
        return classId;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public void setClassId(UUID classId) {
        this.classId = classId;
    }

    public boolean isClassStatus() {
        return classStatus;
    }

    public void setClassStatus(boolean classStatus) {
        this.classStatus = classStatus;
    }

    public Set<String> getUserEmails() {
        return userEmails;
    }

    public void setUserEmails(Set<String> userEmails) {
        this.userEmails = userEmails;
    }
}
