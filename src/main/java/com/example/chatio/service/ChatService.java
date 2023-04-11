package com.example.chatio.service;

import com.example.chatio.model.Chat;

public interface ChatService {

    Chat getChat(String chatName);
    Chat getChat(Long  chatId);

    void registerNewMember();
}
