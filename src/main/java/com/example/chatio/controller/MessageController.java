package com.example.chatio.controller;

import com.example.chatio.model.Message;
import com.example.chatio.model.MessageType;
import com.example.chatio.model.UserProfile;
import com.example.chatio.model.dto.MessageDto;
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
    public Message receivedMessage(MessageDto messageDto){
        UserProfile userProfile = authService.getCurrentUser();

        Message responseMessage = Message.builder()
                .contents(messageDto.getContents())
                .timestamp(LocalDateTime.now())
                .sender(userProfile)
                .messageType(MessageType.CHAT)
                .build();

        messageService.saveMessage(responseMessage);

        return responseMessage;
    }

}
