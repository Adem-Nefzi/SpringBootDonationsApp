package com.example.donationapp2.service;

import com.example.donationapp2.models.Notification;
import java.util.List;

public interface NotificationService {
    Notification saveNotification(Notification notification);
    List<Notification> getAllNotifications();
    Notification getNotificationById(Long id);
    void deleteNotification(Long id);
}
