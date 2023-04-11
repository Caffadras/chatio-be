package com.example.chatio.service;

import com.example.chatio.model.Message;
import com.example.chatio.model.UserProfile;

import java.util.List;

public interface MessageService {
    void saveMessage(Message message);

    List<Message> findAllMessages();

    void sendNewRegistrationMessage(UserProfile registeredUser);
}
