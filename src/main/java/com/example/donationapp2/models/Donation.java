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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DonationOffer getDonationOffer() {
		return donationOffer;
	}

	public void setDonationOffer(DonationOffer donationOffer) {
		this.donationOffer = donationOffer;
	}

	public User getDonor() {
		return donor;
	}

	public void setDonor(User donor) {
		this.donor = donor;
	}

	public User getRecipient() {
		return recipient;
	}

	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public LocalDateTime getHandoverDate() {
		return handoverDate;
	}

	public void setHandoverDate(LocalDateTime handoverDate) {
		this.handoverDate = handoverDate;
	}

	public DonationStatus getStatus() {
		return status;
	}

	public void setStatus(DonationStatus status) {
		this.status = status;
	}

	public String getFeedbackDonor() {
		return feedbackDonor;
	}

	public void setFeedbackDonor(String feedbackDonor) {
		this.feedbackDonor = feedbackDonor;
	}

	public String getFeedbackRecipient() {
		return feedbackRecipient;
	}

	public void setFeedbackRecipient(String feedbackRecipient) {
		this.feedbackRecipient = feedbackRecipient;
	}
    
    
}
