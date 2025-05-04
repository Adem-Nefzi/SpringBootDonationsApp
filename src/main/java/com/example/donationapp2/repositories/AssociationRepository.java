package com.example.donationapp2.repositories;

import com.example.donationapp2.models.Association;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssociationRepository extends JpaRepository<Association, Long> {
    // Find associations by name (case-insensitive, partial match)
    List<Association> findByNameContainingIgnoreCase(String name);

    // Find associations by category (exact match, case-insensitive)
    List<Association> findByCategoryIgnoreCase(String category);

    // Find associations by both name and category
    @Query("SELECT a FROM Association a WHERE " +
            "LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%')) AND " +
            "LOWER(a.category) = LOWER(:category)")
    List<Association> findByNameContainingAndCategoryIgnoreCase(
            @Param("name") String name,
            @Param("category") String category);
}