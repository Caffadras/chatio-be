package com.example.chatio.controller;

import com.example.chatio.model.Message;
import com.example.chatio.model.UserProfile;
import com.example.chatio.security.service.AuthService;
import com.example.chatio.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final AuthService authService;

    @MessageMapping("/chat.send")
    @SendTo("/topic")
    public Message receivedMessage(String message){
        System.out.println("Received message: " + message);
        UserProfile userProfile = authService.getCurrentUser();

        Message responseMessage = Message.builder()
                .contents(message)
                .timestamp(LocalDateTime.now())
                .sender(userProfile)
                .build();

        messageService.saveMessage(responseMessage);

        return responseMessage;
    }

}
