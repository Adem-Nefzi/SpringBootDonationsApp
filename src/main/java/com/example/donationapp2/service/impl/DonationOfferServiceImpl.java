package com.example.donationapp2.service.impl;

import com.example.donationapp2.models.DonationOffer;
import com.example.donationapp2.repositories.DonationOfferRepository;
import com.example.donationapp2.service.DonationOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonationOfferServiceImpl implements DonationOfferService {

    private final DonationOfferRepository donationOfferRepository;

    @Autowired
    public DonationOfferServiceImpl(DonationOfferRepository donationOfferRepository) {
        this.donationOfferRepository = donationOfferRepository;
    }

    @Override
    public DonationOffer saveDonationOffer(DonationOffer donationOffer) {
        return donationOfferRepository.save(donationOffer);
    }

    @Override
    public List<DonationOffer> getAllDonationOffers() {
        return donationOfferRepository.findAll();
    }

    @Override
    public DonationOffer getDonationOfferById(Long id) {
        Optional<DonationOffer> donationOffer = donationOfferRepository.findById(id);
        return donationOffer.orElse(null);
    }

    @Override
    public void deleteDonationOffer(Long id) {
        donationOfferRepository.deleteById(id);
    }
}
