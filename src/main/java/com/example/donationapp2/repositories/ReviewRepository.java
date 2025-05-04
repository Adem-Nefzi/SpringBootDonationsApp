package com.example.donationapp2.repositories;

import com.example.donationapp2.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
