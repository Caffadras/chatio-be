package com.example.chatio.service;

import com.example.chatio.model.Message;
import com.example.chatio.model.UserProfile;

public interface MessageService {
    void saveMessage(Message message);

    void sendNewRegistrationMessage(UserProfile registeredUser);
}
