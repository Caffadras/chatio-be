package com.example.chatio.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

/**
 * We don't want stacktrace to be printed every time when the connection could not be authenticated.
 * So we inherit from {@link org.springframework.web.socket.handler.WebSocketHandlerDecorator} and change this behaviour.
 */
@Slf4j
public class WebSocketJwtAuthHandlerDecorator extends WebSocketHandlerDecorator {
    public WebSocketJwtAuthHandlerDecorator(WebSocketHandler delegate) {
        super(delegate);
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus closeStatus) throws Exception {
        try {
            getDelegate().afterConnectionClosed(session, closeStatus);
        }
        catch (Exception ex) {
            if (log.isWarnEnabled()) {
                if (ex instanceof MessageDeliveryException
                     && ex.getCause() instanceof AccessDeniedException){
                    log.warn("Could not authenticate connection: sessionId " + session.getId());
                }
                else {
                    log.warn("Unhandled exception after connection closed for " + this, ex);
                }
            }

        }
    }
}
