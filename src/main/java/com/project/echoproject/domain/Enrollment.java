package com.project.echoproject.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Enrollment {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "enrollmentID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private UUID enrollmentID;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Classroom Classroom;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Student student;
    private LocalDate createAt;
    private LocalDateTime updateAt;
    private LocalDateTime createdAt;


    public UUID getEnrollmentID() {
        return enrollmentID;
    }

    public void setEnrollmentID(UUID enrollmentID) {
        this.enrollmentID = enrollmentID;
    }

    public com.project.echoproject.domain.Classroom getClassroom() {
        return Classroom;
    }

    public void setClassroom(com.project.echoproject.domain.Classroom classroom) {
        Classroom = classroom;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
