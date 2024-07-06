package com.project.echoproject.domain;

import jakarta.persistence.*;

@Entity(name = "classroom") //테이블명: classroom
public class Classroom{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="classID")
    private Long id;
    @Column
    private String className;
    @Column
    private String professorName;
    @ManyToOne
    private Professor professor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
}
