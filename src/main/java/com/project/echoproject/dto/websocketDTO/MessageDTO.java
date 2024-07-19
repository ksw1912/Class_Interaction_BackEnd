package com.project.echoproject.dto.websocketDTO;

import com.fasterxml.jackson.annotation.JsonCreator;

public class MessageDTO {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    @JsonCreator
    public MessageDTO(String content) {
        this.content = content;
    }
}
