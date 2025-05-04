package com.example.donationapp2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "donations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "offer_id", nullable = false)
    private DonationOffer donationOffer;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "donor_id", nullable = false)
    private User donor;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @Column(name = "handover_date", nullable = false)
    private LocalDateTime handoverDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DonationStatus status;  // Enum: 'scheduled', 'completed', 'cancelled'

    @Column(name = "feedback_donor")
    private String feedbackDonor;

    @Column(name = "feedback_recipient")
    private String feedbackRecipient;

    public enum DonationStatus {
        SCHEDULED, COMPLETED, CANCELLED
    }
}
