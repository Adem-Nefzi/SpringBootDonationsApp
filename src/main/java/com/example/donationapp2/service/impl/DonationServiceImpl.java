package com.example.donationapp2.service.impl;

import com.example.donationapp2.models.Association;
import com.example.donationapp2.models.Donation;
import com.example.donationapp2.models.DonationOffer;
import com.example.donationapp2.repositories.AssociationRepository;
import com.example.donationapp2.repositories.DonationOfferRepository;
import com.example.donationapp2.repositories.DonationRepository;
import com.example.donationapp2.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;
    private final DonationOfferRepository donationOfferRepository;
    private final AssociationRepository associationRepository;

    @Autowired
    public DonationServiceImpl(DonationRepository donationRepository,
                             DonationOfferRepository donationOfferRepository,
                             AssociationRepository associationRepository) {
        this.donationRepository = donationRepository;
        this.donationOfferRepository = donationOfferRepository;
        this.associationRepository = associationRepository;
    }

    @Override
    public Donation saveDonation(Donation donation) {
        return donationRepository.save(donation);
    }

    @Override
    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    @Override
    public Donation getDonationById(Long id) {
        Optional<Donation> donation = donationRepository.findById(id);
        return donation.orElse(null);
    }

    @Override
    public void deleteDonation(Long id) {
        donationRepository.deleteById(id);
    }

    @Override
    public List<Donation> getDonationsByAssociation(Long associationId) {
        return donationRepository.findByRecipientId(associationId);
    }

    @Override
    public Donation assignDonation(Long offerId, Long associationId) {
        DonationOffer offer = donationOfferRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Donation offer not found"));
        
        Association association = associationRepository.findById(associationId)
                .orElseThrow(() -> new RuntimeException("Association not found"));

        Donation donation = new Donation();
        donation.setDonationOffer(offer);
        donation.setDonor(offer.getCreator());
        donation.setRecipient(association.getUser());
        donation.setHandoverDate(LocalDateTime.now().plusDays(3)); // Example: schedule for 3 days from now
        donation.setStatus(Donation.DonationStatus.SCHEDULED);
        
        return donationRepository.save(donation);
    }
}