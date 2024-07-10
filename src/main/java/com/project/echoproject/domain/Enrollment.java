package com.project.echoproject.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
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
    private LocalDate date;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @PrePersist
    protected void onCreate() {
        this.date = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.date = LocalDate.now();
    }
}
