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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        String email = (String) payload.get("associationEmail");

        // Check if email already exists
        if (userService.getUserByEmail(email) != null ||
            associationService.getAssociationByEmail(email) != null) {
            throw new EmailAlreadyExistsException("Email already in use: " + email);
        }

        // Create the association without linking to a user
        Association association = new Association();
        association.setName((String) payload.get("associationName"));
        association.setEmail(email);
        association.setPassword(passwordEncoder.encode((String) payload.get("password")));
        association.setPhone((String) payload.get("associationPhone"));
        association.setAddress((String) payload.get("associationAddress"));
        association.setDescription((String) payload.get("description"));
        association.setCategory((String) payload.get("category"));
        association.setLogoUrl((String) payload.get("logoUrl"));

        // No user assignment needed

        Association savedAssociation = associationService.saveAssociation(association);

        // Set association in session
        session.setAttribute("currentAssociation", savedAssociation);
        session.setAttribute("userType", "association");

        return new ResponseEntity<>(savedAssociation, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials, HttpSession session) {
        System.out.println("Login request received: " + credentials);
        String email = credentials.get("email");
        String password = credentials.get("password");
        String userType = credentials.get("userType"); // This can be "user" or "association"

        // Input validation
        if (email == null || password == null) {
            return ResponseEntity.badRequest().body(
                    Map.of("message", "Email and password are required")
            );
        }

        // Try association login if userType is explicitly set to "association"
        if ("association".equals(userType)) {
            Association association = associationService.getAssociationByEmail(email);

            if (association != null && passwordEncoder.matches(password, association.getPassword())) {
                // Create response with association data
                Map<String, Object> response = new HashMap<>();
                response.put("association", association);

                // Set session attributes
                session.setAttribute("currentAssociation", association);
                session.setAttribute("userType", "association");

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Invalid email or password for association"));
            }
        }

        // Otherwise try user login (default)
        User user = userService.getUserByEmail(email);

        // Authentication check for user
        if (user == null || !passwordEncoder.matches(password, user.getPasswordHash())) {
            // If user login failed, try association login as fallback (only if userType is not specified)
            if (userType == null) {
                Association association = associationService.getAssociationByEmail(email);

                if (association != null && passwordEncoder.matches(password, association.getPassword())) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("association", association);

                    session.setAttribute("currentAssociation", association);
                    session.setAttribute("userType", "association");

                    return ResponseEntity.ok(response);
                }
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid email or password"));
        }

        // User login succeeded
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
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(user.getEmail(), null, new ArrayList<>());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        SecurityContext context = SecurityContextHolder.getContext();
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
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