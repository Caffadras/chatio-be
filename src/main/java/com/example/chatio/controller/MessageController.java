package com.example.chatio.controller;

import com.example.chatio.model.Chat;
import com.example.chatio.model.Message;
import com.example.chatio.model.MessageType;
import com.example.chatio.model.UserProfile;
import com.example.chatio.model.dto.MessageDto;
import com.example.chatio.security.service.AuthService;
import com.example.chatio.service.ChatService;
import com.example.chatio.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final AuthService authService;
    private final ChatService chatService;


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

    @GetMapping("/messages")
    public List<Message> getMessages(){
        return messageService.findAllMessages();
    }


    @GetMapping("/chat/{chatId}")
    public Chat getChatInfo(@PathVariable Long chatId){
        return chatService.getChat(chatId);
    }

}
