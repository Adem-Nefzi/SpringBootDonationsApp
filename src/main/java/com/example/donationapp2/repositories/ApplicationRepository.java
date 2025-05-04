package com.example.donationapp2.repositories;

import com.example.donationapp2.models.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
