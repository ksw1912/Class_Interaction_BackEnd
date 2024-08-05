package com.project.echoproject.controller;

import com.project.echoproject.dto.websocketDTO.MessageDTO;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    @MessageMapping("/classroom/{classroomId}/message")
    @SendTo("/sub/classroom/{classroomId}")
    public MessageDTO classroomCommunication(@DestinationVariable String classroomId, MessageDTO message) throws Exception {
        return message;
    }
}
