package com.example.donationapp2.service.impl;

import com.example.donationapp2.models.Notification;
import com.example.donationapp2.repositories.NotificationRepository;
import com.example.donationapp2.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification saveNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification getNotificationById(Long id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        return notification.orElse(null);
    }

    @Override
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}
