package com.project.echoproject.dto.websocketDTO;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ClassDTO {
    private UUID classId;
    private boolean status = false; //특정 수업open or close
    private Set<String> userEmails; //사용자 참여수

    public ClassDTO(UUID classId, boolean status) {
        this.classId = classId;
        this.status = status;
        this.userEmails = new HashSet<>();
    }

    public UUID getClassId() {
        return classId;
    }

    public void setClassId(UUID classId) {
        this.classId = classId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Set<String> getUserEmails() {
        return userEmails;
    }

    public void setUserEmails(Set<String> userEmails) {
        this.userEmails = userEmails;
    }
}
