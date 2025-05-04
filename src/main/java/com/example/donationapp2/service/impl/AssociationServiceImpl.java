package com.example.donationapp2.service.impl;

import com.example.donationapp2.models.Association;
import com.example.donationapp2.repositories.AssociationRepository;
import com.example.donationapp2.service.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssociationServiceImpl implements AssociationService {

    private final AssociationRepository associationRepository;

    @Autowired
    public AssociationServiceImpl(AssociationRepository associationRepository) {
        this.associationRepository = associationRepository;
    }

    @Override
    public Association saveAssociation(Association association) {
        return associationRepository.save(association);
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
        return associationRepository.findByNameContainingAndCategoryIgnoreCase(name, category);
    }
}