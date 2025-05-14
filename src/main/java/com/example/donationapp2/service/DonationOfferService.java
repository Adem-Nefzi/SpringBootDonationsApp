package com.example.donationapp2.service;

import com.example.donationapp2.models.DonationOffer;
import com.example.donationapp2.models.User;

import java.util.List;

public interface DonationOfferService {
    DonationOffer saveDonationOffer(DonationOffer donationOffer);
    List<DonationOffer> getAllDonationOffers();
    DonationOffer getDonationOfferById(Long id);
    void deleteDonationOffer(Long id);
	void deleteOffer(Long id, User currentUser);
	List<DonationOffer> getActiveOffers();
	List<DonationOffer> getOffersByCreator(User currentUser);
    
}
