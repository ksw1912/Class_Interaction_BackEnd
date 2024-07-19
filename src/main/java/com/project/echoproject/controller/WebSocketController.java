package com.project.echoproject.controller;

import com.project.echoproject.dto.websocketDTO.MessageDTO;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.util.HtmlUtils;

@Controller
public class WebSocketController {
    @MessageMapping("/classroom/{classroomId}/message")
    @SendTo("/topic/classroom/{classroomId}")
    public MessageDTO greeting(@DestinationVariable String classroomId , MessageDTO message, @RequestHeader("Authorization") String token) throws Exception {
        Thread.sleep(1000); // simulated delay 1초후 응답
        return new MessageDTO(message.getContent());
    }






}
