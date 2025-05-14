package com.example.donationapp2.repositories;

import com.example.donationapp2.models.DonationOffer;
import com.example.donationapp2.models.DonationOffer.OfferStatus;
import com.example.donationapp2.models.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationOfferRepository extends JpaRepository<DonationOffer, Long> {
	   	List<DonationOffer> findByStatus(OfferStatus status);
	    List<DonationOffer> findByCreator(User creator);
	    //List<DonationOffer> findByStatusOrderByCreatedAtDesc(OfferStatus status);
	    //List<DonationOffer> findByStatusAndType(OfferStatus status, DonationOffer.OfferType type);
}
