package com.example.donationapp2.service.impl;

import com.example.donationapp2.models.Message;
import com.example.donationapp2.repositories.MessageRepository;
import com.example.donationapp2.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public Message getMessageById(Long id) {
        Optional<Message> message = messageRepository.findById(id);
        return message.orElse(null);
    }

    @Override
    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }
}
