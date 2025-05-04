package com.example.donationapp2.repositories;

import com.example.donationapp2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom method to find a user by email
    User findByEmail(String email);

    User findByUserType(User.UserType userType);
}
