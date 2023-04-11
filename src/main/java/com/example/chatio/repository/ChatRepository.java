package com.example.chatio.repository;

import com.example.chatio.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findByChatName(String chatName);


    @Modifying
    @Query("UPDATE Chat c SET c.membersCount = c.membersCount + 1 WHERE c.chatId = :chatId")
    int incrementMembersCount(@Param("chatId") Long chatId);
}
