package com.example.donationapp2.controllers;

import com.example.donationapp2.Exceptions.EmailAlreadyExistsException;
import com.example.donationapp2.models.Association;
import com.example.donationapp2.models.User;
import com.example.donationapp2.service.AssociationService;
import com.example.donationapp2.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AssociationService associationService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService, AssociationService associationService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.associationService = associationService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register/user")
    public ResponseEntity<?> registerUser(@RequestBody User user, HttpSession session) {
        // Check if email already exists
        if (userService.getUserByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyExistsException("Email already in use: " + user.getEmail());
        }

        // Set created time and encode password
        user.setCreatedAt(LocalDateTime.now());
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));

        // Save user
        User savedUser = userService.saveUser(user);

        // Set user in session
        session.setAttribute("currentUser", savedUser);
        session.setAttribute("userType", "user");

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/register/association")
    public ResponseEntity<?> registerAssociation(@RequestBody Map<String, Object> payload, HttpSession session) {
        // Extract user data
        User user = new User();
        user.setFirstName((String) payload.get("firstName"));
        user.setLastName((String) payload.get("lastName"));
        user.setEmail((String) payload.get("email"));
        user.setPhone((String) payload.get("phone"));
        user.setAddress((String) payload.get("address"));
        user.setUserType(User.UserType.RECIPIENT);
        user.setCreatedAt(LocalDateTime.now());
        user.setPasswordHash(passwordEncoder.encode((String) payload.get("password")));

        // Check if email already exists
        if (userService.getUserByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyExistsException("Email already in use: " + user.getEmail());
        }

        // Save the user first
        User savedUser = userService.saveUser(user);

        // Create the association linked to this user
        Association association = new Association();
        association.setUser(savedUser);
        association.setName((String) payload.get("associationName"));
        association.setEmail((String) payload.get("associationEmail"));
        association.setPassword(passwordEncoder.encode((String) payload.get("password")));
        association.setPhone((String) payload.get("associationPhone"));
        association.setAddress((String) payload.get("associationAddress"));
        association.setDescription((String) payload.get("description"));
        association.setCategory((String) payload.get("category"));
        association.setLogoUrl((String) payload.get("logoUrl"));

        Association savedAssociation = associationService.saveAssociation(association);

        // Set user and association in session
        session.setAttribute("currentUser", savedUser);
        session.setAttribute("currentAssociation", savedAssociation);
        session.setAttribute("userType", "association");

        Map<String, Object> response = new HashMap<>();
        response.put("user", savedUser);
        response.put("association", savedAssociation);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials, HttpSession session) {
        System.out.println("Login request received: " + credentials);
        String email = credentials.get("email");
        String password = credentials.get("password");

        // Input validation
        if (email == null || password == null) {
            return ResponseEntity.badRequest().body(
                    Map.of("message", "Email and password are required")
            );
        }

        User user = userService.getUserByEmail(email);

        // Authentication check
        if (user == null || !passwordEncoder.matches(password, user.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid email or password"));
        }

        // Create consistent response structure
        Map<String, Object> response = new HashMap<>();
        response.put("user", user);

        // Handle association data if recipient
        if (user.getUserType() == User.UserType.RECIPIENT) {
            Association association = findAssociationByUser(user);
            if (association != null) {
                response.put("association", association);
            }
        }

        // Set session attributes
        session.setAttribute("currentUser", user);
        session.setAttribute("userType", user.getUserType().toString().toLowerCase());

        if (response.containsKey("association")) {
            session.setAttribute("currentAssociation", response.get("association"));
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
    }

    // Helper method to find association by user
    private Association findAssociationByUser(User user) {
        // This is a placeholder - you would need to implement a repository method
        // to find an association by user ID
        return associationService.getAllAssociations().stream()
                .filter(a -> a.getUser().getId().equals(user.getId()))
                .findFirst()
                .orElse(null);
    }
}