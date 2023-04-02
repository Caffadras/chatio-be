package com.example.chatio.controller;

import com.example.chatio.model.Message;
import com.example.chatio.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/")
    public String test(){
        Message message = new Message();
        message.setContent("Test Message");
        messageService.saveMessage(message);
        return "Success";
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic")
    public String receivedMessage(String message){
        System.out.println("Received message: " + message);
        return message;
    }

}
