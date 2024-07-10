package com.project.echoproject.dto.websocketDTO;

public class MessageDTO {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageDTO(String content) {
        this.content = content;
    }
}
