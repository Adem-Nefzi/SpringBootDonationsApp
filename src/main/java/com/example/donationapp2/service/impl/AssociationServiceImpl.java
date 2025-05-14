package com.example.donationapp2.service.impl;

import com.example.donationapp2.models.Association;
import com.example.donationapp2.repositories.AssociationRepository;
import com.example.donationapp2.service.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssociationServiceImpl implements AssociationService {

    private final AssociationRepository associationRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AssociationServiceImpl(AssociationRepository associationRepository,
                                PasswordEncoder passwordEncoder) {
        this.associationRepository = associationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Association> getAllAssociations() {
        return associationRepository.findAll();
    }

    @Override
    public Association getAssociationById(Long id) {
        Optional<Association> association = associationRepository.findById(id);
        return association.orElse(null);
    }

    @Override
    public Association saveAssociation(Association association) {
        if (association.getPassword() != null) {
            association.setPassword(passwordEncoder.encode(association.getPassword()));
        }
        return associationRepository.save(association);
    }

    @Override
    public void deleteAssociation(Long id) {
        associationRepository.deleteById(id);
    }

    @Override
    public List<Association> findAssociationsByName(String name) {
        return associationRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Association> findAssociationsByCategory(String category) {
        return associationRepository.findByCategoryIgnoreCase(category);
    }

    @Override
    public List<Association> findAssociationsByNameAndCategory(String name, String category) {
        return associationRepository.findByNameContainingIgnoreCaseAndCategoryIgnoreCase(name, category);
    }

    @Override
    public Association updateAssociationProfile(Long id, Association updated, PasswordEncoder encoder) {
        Association existing = associationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Association not found"));

        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        existing.setAddress(updated.getAddress());
        existing.setDescription(updated.getDescription());
        existing.setCategory(updated.getCategory());
        existing.setLogoUrl(updated.getLogoUrl());

        if (updated.getPassword() != null && !updated.getPassword().isEmpty()) {
            existing.setPassword(encoder.encode(updated.getPassword()));
        }

        return associationRepository.save(existing);
    }

	@Override
	public List<Association> searchAssociations(String name, String category) {

		return null;
	}

    @Override
    public Association getAssociationByEmail(String email) {
        return associationRepository.findByEmailIgnoreCase(email);
    }
}