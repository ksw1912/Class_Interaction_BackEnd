package com.project.echoproject.controller;

import com.project.echoproject.dto.websocketDTO.MessageDTO;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.HtmlUtils;

@Controller
public class WebSocketController {
    @MessageMapping("/classroom/{classroomId}/message")
    @SendTo("/sub/classroom/{classroomId}")
    public MessageDTO classroomCommunication(@DestinationVariable String classroomId , MessageDTO message) throws Exception {
        Thread.sleep(1000);
        return message;
    }
}
