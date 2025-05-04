package com.example.donationapp2.service;

import com.example.donationapp2.models.Application;
import java.util.List;

public interface ApplicationService {
    Application saveApplication(Application application);
    List<Application> getAllApplications();
    Application getApplicationById(Long id);
    void deleteApplication(Long id);
}
