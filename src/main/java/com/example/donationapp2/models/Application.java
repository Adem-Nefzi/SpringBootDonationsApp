package com.example.donationapp2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "offer_id", nullable = false)
    private DonationOffer donationOffer;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "applicant_id", nullable = false)
    private User applicant;

    @Column(name = "message")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ApplicationStatus status;  // Enum: 'pending', 'approved', 'rejected'

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "response_message")
    private String responseMessage;  // Optional response from donor

    public enum ApplicationStatus {
        PENDING, APPROVED, REJECTED
    }
}
