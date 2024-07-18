package com.project.echoproject.dto.classroom;

import java.util.List;
import java.util.UUID;

public class ClassroomDTO {
    private String ClassName;
    private List<String> ops;

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public List<String> getOps() {
        return ops;
    }

    public void setOps(List<String> ops) {
        this.ops = ops;
    }
}
