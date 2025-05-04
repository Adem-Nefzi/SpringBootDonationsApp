package com.example.donationapp2.service.impl;

import com.example.donationapp2.models.Donation;
import com.example.donationapp2.repositories.DonationRepository;
import com.example.donationapp2.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;

    @Autowired
    public DonationServiceImpl(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
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
}
