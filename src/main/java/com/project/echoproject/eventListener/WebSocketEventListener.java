package com.project.echoproject.eventListener;

import com.project.echoproject.dto.websocketDTO.MessageDTO;
import com.project.echoproject.jwt.JWTUtil;
import com.project.echoproject.service.WebsocketService;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.util.UUID;

@Component
public class WebSocketEventListener {
    private SimpMessageSendingOperations messagingTemplate;
    private JWTUtil jwtUtil;
    private WebsocketService websocketService;

    public WebSocketEventListener(SimpMessageSendingOperations messagingTemplate, JWTUtil jwtUtil, WebsocketService websocketService) {
        this.messagingTemplate = messagingTemplate;
        this.jwtUtil = jwtUtil;
        this.websocketService = websocketService;
    }

    @EventListener
    public void handleWebSocketSubscribeEventListener(SessionSubscribeEvent event) { //세션 연결할 때 발생`
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String email = (String) accessor.getSessionAttributes().get(accessor.getSessionId() + "email");
        String role = (String) accessor.getSessionAttributes().get(accessor.getSessionId() + "role");
        String classIdString = accessor.getDestination();
        UUID classId = UUID.fromString(classIdString.split("/")[3]);
        accessor.getSessionAttributes().put(accessor.getSessionId() + "classId", classId);

        //교육자일경우 방생성
        if (role.equals("instructor")) {
            websocketService.createRoom(classId);
        }

        //학생 방에 존재 하지 않을 경우
        if (!websocketService.roomExists(classId)) {
            MessageDTO messageDTO = new MessageDTO(MessageDTO.Status.CLOSE, null, null, null, null, 0, null, false);
            messageDTO.setStatus(MessageDTO.Status.CLOSE);
            messagingTemplate.convertAndSend("/sub/classroom/" + classId, messageDTO);
        }
        // email 사용자 입장 인원 추가
        websocketService.addUserEmail(classId, email);

        MessageDTO messageDTO = websocketService.getRooms().get(classId);
        messageDTO.setStatus(MessageDTO.Status.PEOPLESTATUS);

        //사용자 정보 접속 인원 보내주기
        messagingTemplate.convertAndSend("/sub/classroom/" + classId, messageDTO);
    }

    //    SessionDisconnectEvent
    @EventListener
    public void handleTest(SessionDisconnectEvent event) {

    }

    //SessionUnsubscribeEvent
    //SessionDisconnectEvent
    @EventListener
    public void handleWebSocketDisconnectionListener(SessionUnsubscribeEvent event) { //STOMP session 이 끝났을 때 발생
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String email = (String) accessor.getSessionAttributes().get(accessor.getSessionId() + "email");
        String role = (String) accessor.getSessionAttributes().get(accessor.getSessionId() + "role");
        UUID classId = (UUID) accessor.getSessionAttributes().get(accessor.getSessionId() + "classId");

        if (role.equals("instructor")) {
            MessageDTO messageDTO = websocketService.getRooms().get(classId);
            messageDTO.setStatus(MessageDTO.Status.CLOSE);
            websocketService.closeRoom(classId);
            messagingTemplate.convertAndSend("/sub/classroom/" + classId, messageDTO);
        } else {
            websocketService.removeUserEmailFromRoom(classId, email);
            MessageDTO messageDTO = websocketService.getRooms().get(classId);
            messageDTO.setStatus(MessageDTO.Status.PEOPLESTATUS);
            messagingTemplate.convertAndSend("/sub/classroom/" + classId, messageDTO);
        }
        accessor.getSessionAttributes().remove(accessor.getSessionId() + "email");
        accessor.getSessionAttributes().remove(accessor.getSessionId() + "role");
        accessor.getSessionAttributes().remove(accessor.getSessionId() + "classId");

    }
}
