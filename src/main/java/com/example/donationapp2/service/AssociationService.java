package com.example.donationapp2.service;

import com.example.donationapp2.models.Association;
import java.util.List;

public interface AssociationService {
    Association saveAssociation(Association association);
    List<Association> getAllAssociations();
    Association getAssociationById(Long id);
    void deleteAssociation(Long id);

    // New methods
    List<Association> findAssociationsByName(String name);
    List<Association> findAssociationsByCategory(String category);
    List<Association> findAssociationsByNameAndCategory(String name, String category);
}