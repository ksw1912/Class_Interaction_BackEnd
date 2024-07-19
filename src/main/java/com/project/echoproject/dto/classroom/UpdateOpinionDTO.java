package com.project.echoproject.dto.classroom;

import java.util.List;
import java.util.UUID;

public class UpdateOpinionDTO {
    UUID classId;
    List<String> opinion;

    public UUID getClassId() {
        return classId;
    }

    public void setClassId(UUID classId) {
        this.classId = classId;
    }

    public List<String> getOpinion() {
        return opinion;
    }

    public void setOpinion(List<String> opinion) {
        this.opinion = opinion;
    }
}
