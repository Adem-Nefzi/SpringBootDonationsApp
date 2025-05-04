package com.example.donationapp2.controllers;

import com.example.donationapp2.models.User;
import com.example.donationapp2.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin (origins ="*")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // Get current user profile
    @GetMapping("/profile")
    public ResponseEntity<?> getCurrentUserProfile(HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return new ResponseEntity<>("Not logged in", HttpStatus.UNAUTHORIZED);
        }

        // Refresh user data from database
        User user = userService.getUserById(currentUser.getId());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Update current user profile
    @PutMapping("/profile")
    public ResponseEntity<?> updateUserProfile(@RequestBody User updatedUser, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return new ResponseEntity<>("Not logged in", HttpStatus.UNAUTHORIZED);
        }

        // Get the latest user data from the database
        User user = userService.getUserById(currentUser.getId());
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        // Update fields (excluding sensitive ones)
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setPhone(updatedUser.getPhone());
        user.setAddress(updatedUser.getAddress());
        user.setBio(updatedUser.getBio());
        user.setProfilePicture(updatedUser.getProfilePicture());
        user.setUpdatedAt(LocalDateTime.now());

        // Only update password if provided
        if (updatedUser.getPasswordHash() != null && !updatedUser.getPasswordHash().isEmpty()) {
            user.setPasswordHash(passwordEncoder.encode(updatedUser.getPasswordHash()));
        }

        // Save updated user
        User savedUser = userService.saveUser(user);

        // Update session
        session.setAttribute("currentUser", savedUser);

        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    // Delete current user account
    @DeleteMapping("/profile")
    public ResponseEntity<?> deleteUserProfile(HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return new ResponseEntity<>("Not logged in", HttpStatus.UNAUTHORIZED);
        }

        userService.deleteUser(currentUser.getId());
        session.invalidate();

        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

    // Admin endpoints for user management

    // Get all users (Admin only)
    @GetMapping("/admin/all")
    public ResponseEntity<?> getAllUsers(HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || currentUser.getUserType() != User.UserType.ADMIN) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
        }

        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Get user by ID (Admin only)
    @GetMapping("/admin/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || currentUser.getUserType() != User.UserType.ADMIN) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
        }

        User user = userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Update user by ID (Admin only)
    @PutMapping("/admin/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || currentUser.getUserType() != User.UserType.ADMIN) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
        }

        User user = userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        // Update fields
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setPhone(updatedUser.getPhone());
        user.setAddress(updatedUser.getAddress());
        user.setBio(updatedUser.getBio());
        user.setProfilePicture(updatedUser.getProfilePicture());
        user.setUserType(updatedUser.getUserType());
        user.setUpdatedAt(LocalDateTime.now());

        // Only update password if provided
        if (updatedUser.getPasswordHash() != null && !updatedUser.getPasswordHash().isEmpty()) {
            user.setPasswordHash(passwordEncoder.encode(updatedUser.getPasswordHash()));
        }

        User savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    // Delete user by ID (Admin only)
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || currentUser.getUserType() != User.UserType.ADMIN) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
        }

        User user = userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }
}