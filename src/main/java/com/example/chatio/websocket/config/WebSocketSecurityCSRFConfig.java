package com.example.chatio.websocket.config;

import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.stereotype.Component;

/**
 * We need this configuration to disable csrf.
 * Spring states: "At this point, CSRF is not configurable when using
 * {@link org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity},
 * though this will likely be added in a future release." - So we have to make a separate
 * config file, that extends deprecated class and disables csrf.
 */
@Component
public class WebSocketSecurityCSRFConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {


    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
                .anyMessage().authenticated();
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true; // Disable CSRF for WebSockets
    }
}
