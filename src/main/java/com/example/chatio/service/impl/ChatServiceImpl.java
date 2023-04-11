package com.example.chatio.service.impl;

import com.example.chatio.exception.ChatNotFoundException;
import com.example.chatio.model.Chat;
import com.example.chatio.repository.ChatRepository;
import com.example.chatio.service.ChatService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    @Override
    public Chat getChat(String chatName) {
        return chatRepository.findByChatName(chatName)
                .orElseThrow(() -> new ChatNotFoundException("Chat '" + chatName + "' was not found."));
    }

    @Override
    public Chat getChat(Long chatId) {
        return chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatNotFoundException("Chat with id:" + chatId + " was not found."));
    }

    @Override
    @Transactional
    public void registerNewMember() {
        //for now, we have only 1 chat, so we hardcode its id.
        int updatedRowCount = chatRepository.incrementMembersCount(1L);
        if (updatedRowCount == 0){
            throw new ChatNotFoundException();
        }
    }




}
