package com.project.echoproject.Handler;

import com.project.echoproject.domain.User;
import com.project.echoproject.dto.CustomUserDetails;
import com.project.echoproject.jwt.JWTUtil;
import com.project.echoproject.service.WebsocketService;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class StompHandler implements ChannelInterceptor {
    private JWTUtil jwtUtil;
    private String token;
    private WebsocketService websocketService;

    public StompHandler(JWTUtil jwtUtil, WebsocketService websocketService) {
        this.jwtUtil = jwtUtil;
        this.websocketService = websocketService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            token = String.valueOf(accessor.getNativeHeader("Authorization"));

            if (token == null) {
                throw new IllegalArgumentException("토큰 X");
            }

            token = token.split(" ")[1];
            token = token.substring(0, token.length() - 1);

            if (jwtUtil.isExpired(token)) {
                throw new IllegalArgumentException("토큰 expired");
            }
            String email = jwtUtil.getEmail(token);
            String role = jwtUtil.getRole(token);
            accessor.getSessionAttributes().put(accessor.getSessionId()+"email", email);
            accessor.getSessionAttributes().put(accessor.getSessionId()+"role", role);

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
}
