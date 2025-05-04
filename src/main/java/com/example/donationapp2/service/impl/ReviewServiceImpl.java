package com.example.donationapp2.service.impl;

import com.example.donationapp2.models.Review;
import com.example.donationapp2.repositories.ReviewRepository;
import com.example.donationapp2.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public Review getReviewById(Long id) {
        Optional<Review> review = reviewRepository.findById(id);
        return review.orElse(null);
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
