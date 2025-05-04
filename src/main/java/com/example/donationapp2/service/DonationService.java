package com.example.donationapp2.service;

import com.example.donationapp2.models.Donation;
import java.util.List;

public interface DonationService {
    Donation saveDonation(Donation donation);
    List<Donation> getAllDonations();
    Donation getDonationById(Long id);
    void deleteDonation(Long id);
}
