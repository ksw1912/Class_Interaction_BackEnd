package com.project.echoproject.eventListener;

import com.project.echoproject.jwt.JWTUtil;
import com.project.echoproject.service.WebsocketService;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
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
        String classIdString = accessor.getDestination();
        System.out.println(classIdString);

        String token = String.valueOf(accessor.getNativeHeader("Authorization"));
        System.out.println(token);
        token = token.split(" ")[1];
        token = token.substring(0, token.length() - 1);

        String email = jwtUtil.getEmail(token);
        String role = jwtUtil.getRole(token);

        UUID classId = UUID.fromString(classIdString.split("/")[2]);
        System.out.println(classId);
        //교육자일경우 방생성
        if (role.equals("instructor")) {
            websocketService.createRoom(classId);
        }
        //학생 방에 존재 하지 않을 경우
       if(!websocketService.roomExists(classId)){
           throw new IllegalArgumentException("방이 존재X");
       }
        // email 사용자 입장 인원 추가
        websocketService.addUserEmail(classId, email);

        //사용자 정보 접속 인원 보내주기
        messagingTemplate.convertAndSend("/topic/" + classId + "/classMember", websocketService.getUserCountInRoom(classId));
    }

    @EventListener
    public void handleWebSocketDisconnectionListener(SessionUnsubscribeEvent event) { //STOMP session 이 끝났을 때 발생
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
////        String email = jwtUtil.getEmail(token);
////        String role = jwtUtil.getRole(token);
//        String classIdString = accessor.getFirstNativeHeader("classId");
//
//        if(role.equals("student")){
//            websocketService.getUserCountInRoom(classId,email);
//            System.out.println("사용자 퇴장");
//        }
//        else{
//            websocketService.getRooms().remove(classId);
//            System.out.println("방삭제");
//        }
//        messagingTemplate.convertAndSend("/topic/" + classId + "/classMember", websocketService.getUserCountInRoom(classId));
    }
}
