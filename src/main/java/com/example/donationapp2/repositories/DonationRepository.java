package com.example.donationapp2.repositories;

import com.example.donationapp2.models.Donation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation, Long> {

	List<Donation> findByRecipientId(Long associationId);
}
