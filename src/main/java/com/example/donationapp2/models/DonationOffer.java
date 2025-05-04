package com.example.donationapp2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "donation_offers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonationOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;  // This can be either a user or an association

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private OfferType type;  // Enum: 'food', 'clothes', 'medicine', 'other'

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "item_condition")
    private String condition;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @Column(name = "location")
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OfferStatus status;  // Enum: 'active', 'fulfilled', 'expired'

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ElementCollection
    @CollectionTable(name = "offer_images", joinColumns = @JoinColumn(name = "donation_offer_id"))
    @Column(name = "image_url")
    private List<String> imagesUrls;

    public enum OfferType {
        FOOD, CLOTHES, MEDICINE, OTHER
    }

    public enum OfferStatus {
        ACTIVE, FULFILLED, EXPIRED
    }
}
