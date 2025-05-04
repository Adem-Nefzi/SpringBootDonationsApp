package com.example.donationapp2.repositories;

import com.example.donationapp2.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
