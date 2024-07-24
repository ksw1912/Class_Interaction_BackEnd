package com.project.echoproject.eventListener;

import com.project.echoproject.jwt.JWTUtil;
import com.project.echoproject.service.WebsocketService;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
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
    public void handleWebSocketSubscribeEventListener(SessionSubscribeEvent event) { //세션 연결할 때 발생
        System.out.println("사용자 입장");

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String email = (String) accessor.getSessionAttributes().get(accessor.getSessionId()+"email");
        String role = (String) accessor.getSessionAttributes().get(accessor.getSessionId()+"role");
        String classIdString = accessor.getDestination();
        UUID classId = UUID.fromString(classIdString.split("/")[3]);

        //교육자일경우 방생성
        if (role.equals("instructor")) {
            websocketService.createRoom(classId);
        }
        //학생 방에 존재 하지 않을 경우
        if (!websocketService.roomExists(classId)) {
            throw new IllegalArgumentException("방이 존재X");
        }
        // email 사용자 입장 인원 추가
        websocketService.addUserEmail(classId, email);

        //사용자 정보 접속 인원 보내주기
        messagingTemplate.convertAndSend("/topic/classroom/" + classId, websocketService.getUserCountInRoom(classId));
    }

    //SessionUnsubscribeEvent 테스트 X
    @EventListener
    public void handleWebSocketDisconnectionListener(SessionUnsubscribeEvent event) { //STOMP session 이 끝났을 때 발생
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String email = (String) accessor.getSessionAttributes().get(accessor.getSessionId()+"email");
        String role = (String) accessor.getSessionAttributes().get(accessor.getSessionId()+"role");
        String classIdString = accessor.getSubscriptionId();
        System.out.println("test"+ classIdString);
        String[] splitResult = classIdString.split("/");
        UUID classId = null;
        if (splitResult.length > 3) {
            classId = UUID.fromString(splitResult[3]);
        } else {
            System.out.println("Invalid subscription ID format");
            return;
        }


        if (email == null || role == null || classId == null) {
            System.out.println("Invalid session attributes or destination");
            return;
        }

        if (role.equals("student")) {
            websocketService.removeUserEmailFromRoom(classId, email);
            System.out.println("사용자 퇴장");
            messagingTemplate.convertAndSend("/sub/classroom/" + classId, websocketService.getUserCountInRoom(classId));
        } else {
            websocketService.closeRoom(classId);
            System.out.println("방삭제");
            messagingTemplate.convertAndSend("/sub/classroom/" + classId, "close");
        }
        accessor.getSessionAttributes().remove(accessor.getSessionId()+"email");
        accessor.getSessionAttributes().remove(accessor.getSessionId()+"role");
    }
}
