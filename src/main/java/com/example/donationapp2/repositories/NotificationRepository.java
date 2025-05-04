package com.example.donationapp2.repositories;

import com.example.donationapp2.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
