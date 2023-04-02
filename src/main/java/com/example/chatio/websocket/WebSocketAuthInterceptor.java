package com.example.chatio.websocket;

import com.example.chatio.security.service.JwtTokenAuthenticator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    private final JwtTokenAuthenticator jwtTokenAuthenticator;
    @Override
    public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (Objects.isNull(headerAccessor) || headerAccessor.getCommand() != StompCommand.CONNECT){
            return message;
        }

        String authHeader = headerAccessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION);
        log.debug("Trying to authenticate web socket connection with sessionId: " + headerAccessor.getSessionId());
        try{
            jwtTokenAuthenticator.authenticateWithHeader(authHeader);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            log.info("Authenticated web socket connection for user: " + authentication.getName());
            headerAccessor.setUser(authentication);
        }catch (AuthenticationException ignored){
            //could not authenticate, but no action is required
        }
        return message;
    }

}
