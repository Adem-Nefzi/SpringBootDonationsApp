package com.example.donationapp2.controllers;

import com.example.donationapp2.models.Association;
import com.example.donationapp2.models.User;
import com.example.donationapp2.service.AssociationService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/associations")
@CrossOrigin (origins ="*")
public class AssociationController {

    private final AssociationService associationService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AssociationController(AssociationService associationService, PasswordEncoder passwordEncoder) {
        this.associationService = associationService;
        this.passwordEncoder = passwordEncoder;
    }

    // Get all associations (accessible to all users)
    @GetMapping
    public ResponseEntity<List<Association>> getAllAssociations() {
        List<Association> associations = associationService.getAllAssociations();
        return new ResponseEntity<>(associations, HttpStatus.OK);
    }

    // Get association by ID (accessible to all users)
    @GetMapping("/{id}")
    public ResponseEntity<?> getAssociationById(@PathVariable Long id) {
        Association association = associationService.getAssociationById(id);
        if (association == null) {
            return new ResponseEntity<>("Association not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(association, HttpStatus.OK);
    }

    // Search associations by name (accessible to all users)
    @GetMapping("/search")
    public ResponseEntity<List<Association>> searchAssociations(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category) {

        List<Association> associations;

        if (name != null && category != null) {
            associations = associationService.findAssociationsByNameAndCategory(name, category);
        } else if (name != null) {
            associations = associationService.findAssociationsByName(name);
        } else if (category != null) {
            associations = associationService.findAssociationsByCategory(category);
        } else {
            associations = associationService.getAllAssociations();
        }

        return new ResponseEntity<>(associations, HttpStatus.OK);
    }

    // Get current association profile (for logged-in associations)
    @GetMapping("/profile")
    public ResponseEntity<?> getCurrentAssociationProfile(HttpSession session) {
        Association currentAssociation = (Association) session.getAttribute("currentAssociation");
        if (currentAssociation == null) {
            return new ResponseEntity<>("Not logged in as association", HttpStatus.UNAUTHORIZED);
        }

        // Refresh association data from database
        Association association = associationService.getAssociationById(currentAssociation.getId());
        return new ResponseEntity<>(association, HttpStatus.OK);
    }

    // Update current association profile (for logged-in associations)
    @PutMapping("/profile")
    public ResponseEntity<?> updateAssociationProfile(@RequestBody Association updatedAssociation, HttpSession session) {
        Association currentAssociation = (Association) session.getAttribute("currentAssociation");
        if (currentAssociation == null) {
            return new ResponseEntity<>("Not logged in as association", HttpStatus.UNAUTHORIZED);
        }

        // Get the latest association data from the database
        Association association = associationService.getAssociationById(currentAssociation.getId());
        if (association == null) {
            return new ResponseEntity<>("Association not found", HttpStatus.NOT_FOUND);
        }

        // Update fields
        association.setName(updatedAssociation.getName());
        association.setEmail(updatedAssociation.getEmail());
        association.setPhone(updatedAssociation.getPhone());
        association.setAddress(updatedAssociation.getAddress());
        association.setDescription(updatedAssociation.getDescription());
        association.setCategory(updatedAssociation.getCategory());
        association.setLogoUrl(updatedAssociation.getLogoUrl());
        association.setFoundationDate(updatedAssociation.getFoundationDate());

        // Only update password if provided
        if (updatedAssociation.getPassword() != null && !updatedAssociation.getPassword().isEmpty()) {
            association.setPassword(passwordEncoder.encode(updatedAssociation.getPassword()));
        }

        // Save updated association
        Association savedAssociation = associationService.saveAssociation(association);

        // Update session
        session.setAttribute("currentAssociation", savedAssociation);

        return new ResponseEntity<>(savedAssociation, HttpStatus.OK);
    }

    // Delete current association (for logged-in associations)
    @DeleteMapping("/profile")
    public ResponseEntity<?> deleteAssociationProfile(HttpSession session) {
        Association currentAssociation = (Association) session.getAttribute("currentAssociation");
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentAssociation == null || currentUser == null) {
            return new ResponseEntity<>("Not logged in as association", HttpStatus.UNAUTHORIZED);
        }

        associationService.deleteAssociation(currentAssociation.getId());
        session.removeAttribute("currentAssociation");

        return new ResponseEntity<>("Association deleted successfully", HttpStatus.OK);
    }

    // Admin endpoints for association management

    // Admin: Create new association
    @PostMapping("/admin")
    public ResponseEntity<?> createAssociation(@RequestBody Association association, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || currentUser.getUserType() != User.UserType.ADMIN) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
        }

        if (association.getPassword() != null) {
            association.setPassword(passwordEncoder.encode(association.getPassword()));
        }

        Association savedAssociation = associationService.saveAssociation(association);
        return new ResponseEntity<>(savedAssociation, HttpStatus.CREATED);
    }

    // Admin: Update association by ID
    @PutMapping("/admin/{id}")
    public ResponseEntity<?> updateAssociation(@PathVariable Long id, @RequestBody Association updatedAssociation, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || currentUser.getUserType() != User.UserType.ADMIN) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
        }

        Association association = associationService.getAssociationById(id);
        if (association == null) {
            return new ResponseEntity<>("Association not found", HttpStatus.NOT_FOUND);
        }

        // Update fields
        association.setName(updatedAssociation.getName());
        association.setEmail(updatedAssociation.getEmail());
        association.setPhone(updatedAssociation.getPhone());
        association.setAddress(updatedAssociation.getAddress());
        association.setDescription(updatedAssociation.getDescription());
        association.setCategory(updatedAssociation.getCategory());
        association.setLogoUrl(updatedAssociation.getLogoUrl());
        association.setFoundationDate(updatedAssociation.getFoundationDate());
        association.setUser(updatedAssociation.getUser());

        // Only update password if provided
        if (updatedAssociation.getPassword() != null && !updatedAssociation.getPassword().isEmpty()) {
            association.setPassword(passwordEncoder.encode(updatedAssociation.getPassword()));
        }

        Association savedAssociation = associationService.saveAssociation(association);
        return new ResponseEntity<>(savedAssociation, HttpStatus.OK);
    }

    // Admin: Delete association by ID
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteAssociation(@PathVariable Long id, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || currentUser.getUserType() != User.UserType.ADMIN) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
        }

        Association association = associationService.getAssociationById(id);
        if (association == null) {
            return new ResponseEntity<>("Association not found", HttpStatus.NOT_FOUND);
        }

        associationService.deleteAssociation(id);
        return new ResponseEntity<>("Association deleted successfully", HttpStatus.OK);
    }
}