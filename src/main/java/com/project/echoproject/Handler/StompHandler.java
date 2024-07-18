package com.project.echoproject.Handler;

import com.project.echoproject.domain.User;
import com.project.echoproject.dto.CustomUserDetails;
import com.project.echoproject.jwt.JWTUtil;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class StompHandler implements ChannelInterceptor {
    private JWTUtil jwtUtil;

    public StompHandler(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = String.valueOf(accessor.getHeader("Authorization"));
            System.out.println(accessor.getHeader("Authorization"));
            System.out.println(accessor.getNativeHeader("Authorization"));
            System.out.println("websocket :" +token);
            if (token == null || !token.startsWith("Bearer ")) {
                System.out.println("웹소켓에서 토큰이없음");
                throw new IllegalArgumentException("토큰 X");
            }
            token = token.split(" ")[1];
            if (jwtUtil.isExpired(token)) {
                System.out.println("토큰 expired");
                throw new IllegalArgumentException("토큰 expired");
            }
            String email = jwtUtil.getEmail(token);
            String role = jwtUtil.getRole(token);
            System.out.println("JWTFilter 클래스: " + email + " " + role);

            //userEntity를 생성하여 값 set
            User userEntity = new User();
            userEntity.setEmail(email);
            userEntity.setPassword("temppassword"); //token이라 막지정
            userEntity.setRole(role);

            //UserDetails에 회원 정보 객체 담기
            CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

            //스프링 시큐리티 인증 토큰 생성
            Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
            //세션에 사용자 등록
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        return message;
    }

    @EventListener
    public void handleWebSocketConnectionListener(SessionConnectedEvent event) {
        System.out.println("사용자 입장");
    }

    @EventListener
    public void handleWebSocketDisconnectionListener(SessionDisconnectEvent event) {
        System.out.println("사용자 퇴장");
    }

}