package com.project.echoproject.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Opinion {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "opinionId",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private UUID opinionId;


    @ManyToOne
    @JoinColumn(name = "classId")
    private Classroom classroom;
    @Column
    private String opinion;

    public UUID getOpinionId() {
        return opinionId;
    }

    public void setOpinionId(UUID opinionId) {
        this.opinionId = opinionId;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }
}
