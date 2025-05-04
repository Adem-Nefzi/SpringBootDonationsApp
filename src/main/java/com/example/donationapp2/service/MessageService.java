package com.example.donationapp2.service;

import com.example.donationapp2.models.Message;
import java.util.List;

public interface MessageService {
    Message saveMessage(Message message);
    List<Message> getAllMessages();
    Message getMessageById(Long id);
    void deleteMessage(Long id);
}
