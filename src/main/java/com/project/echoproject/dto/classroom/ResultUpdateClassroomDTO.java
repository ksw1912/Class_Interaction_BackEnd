package com.project.echoproject.dto.classroom;

import com.project.echoproject.domain.Classroom;
import com.project.echoproject.domain.Opinion;

import java.util.List;

public class ResultUpdateClassroomDTO {
    Classroom classroom;
    List<Opinion> opinions;

    public ResultUpdateClassroomDTO(Classroom classroom, List<Opinion> opinions) {
        this.classroom = classroom;
        this.opinions = opinions;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public List<Opinion> getOpinions() {
        return opinions;
    }

    public void setOpinions(List<Opinion> opinions) {
        this.opinions = opinions;
    }
}
