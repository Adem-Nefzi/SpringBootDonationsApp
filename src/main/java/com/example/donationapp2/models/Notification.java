package com.example.donationapp2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private NotificationType type;  // Enum: 'application', 'approval', 'message', 'system'

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "related_offer_id")
    private DonationOffer relatedOffer;  // Nullable FK to DonationOffer

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "related_application_id")
    private Application relatedApplication;  // Nullable FK to Application

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "related_message_id")
    private Message relatedMessage;  // Nullable FK to Message

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public enum NotificationType {
        APPLICATION, APPROVAL, MESSAGE, SYSTEM
    }
}
