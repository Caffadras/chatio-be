package com.example.chatio.service.impl;

import com.example.chatio.model.Message;
import com.example.chatio.repository.MessageRepository;
import com.example.chatio.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    public void saveMessage(Message message) {
        messageRepository.save(message);
    }
}
