package com.example.chatio.service.impl;

import com.example.chatio.model.Message;
import com.example.chatio.model.MessageType;
import com.example.chatio.model.UserProfile;
import com.example.chatio.repository.MessageRepository;
import com.example.chatio.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    @Override
    public List<Message> findAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public void sendNewRegistrationMessage(UserProfile registeredUser) {
        Message registrationMessage = Message.builder()
                .timestamp(LocalDateTime.now())
                .sender(registeredUser)
                .messageType(MessageType.JOIN)
                .build();


        messageRepository.save(registrationMessage);
        simpMessagingTemplate.convertAndSend("/topic", registrationMessage);

    }
}
