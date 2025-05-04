package com.example.donationapp2.repositories;

import com.example.donationapp2.models.DonationOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationOfferRepository extends JpaRepository<DonationOffer, Long> {
}
