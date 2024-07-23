package com.project.echoproject.dto.classroom;

import com.project.echoproject.domain.Classroom;

import java.util.List;
import java.util.UUID;

public class UpdateClassroomDTO {
    Classroom classroom;
    List<String> opinion;

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public List<String> getOpinion() {
        return opinion;
    }

    public void setOpinion(List<String> opinion) {
        this.opinion = opinion;
    }
}
