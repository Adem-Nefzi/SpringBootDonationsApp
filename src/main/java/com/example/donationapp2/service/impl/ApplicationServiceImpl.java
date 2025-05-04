package com.example.donationapp2.service.impl;

import com.example.donationapp2.models.Application;
import com.example.donationapp2.repositories.ApplicationRepository;
import com.example.donationapp2.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationServiceImpl(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public Application saveApplication(Application application) {
        return applicationRepository.save(application);
    }

    @Override
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    @Override
    public Application getApplicationById(Long id) {
        Optional<Application> application = applicationRepository.findById(id);
        return application.orElse(null);
    }

    @Override
    public void deleteApplication(Long id) {
        applicationRepository.deleteById(id);
    }
}
