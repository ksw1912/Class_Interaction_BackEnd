package com.project.echoproject.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity(name = "classroom") //테이블명: classroom
public class Classroom{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "classId",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID classId;
    @ManyToOne //many: 클래스룸 one: 교육자
    @JoinColumn(nullable = false)
    private Instructor instructor;
    @Column
    private String className;
    @Column
    private String instructorName;

    public UUID getClassId() {
        return classId;
    }

    public void setClassId(UUID classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }
}
