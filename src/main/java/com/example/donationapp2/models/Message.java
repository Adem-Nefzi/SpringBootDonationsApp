package com.example.donationapp2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "related_offer_id")
    private DonationOffer relatedOffer;  // Optional FK to DonationOffer

    @Column(name = "message_content", nullable = false)
    private String messageContent;

    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt;

    @Column(name = "read_at")
    private LocalDateTime readAt;
}
