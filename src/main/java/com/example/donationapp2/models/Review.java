package com.example.donationapp2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "reviewer_id", nullable = false)
    private User reviewer;



    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "reviewed_id", nullable = false)
    private User reviewed;



    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "donation_id", nullable = false)
    private Donation donation;


    private Integer rating; // Rating between 1-5 stars

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
