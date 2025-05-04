package com.example.donationapp2.service;

import com.example.donationapp2.models.Review;
import java.util.List;

public interface ReviewService {
    Review saveReview(Review review);
    List<Review> getAllReviews();
    Review getReviewById(Long id);
    void deleteReview(Long id);
}
