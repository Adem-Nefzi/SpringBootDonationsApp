package com.example.donationapp2;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Donationapp2Application {
	 
    public static void main(String[] args) {
        SpringApplication.run(Donationapp2Application.class, args);
    }
/*
    @Component
    public class DataInitializer implements CommandLineRunner {

        @Autowired
        private UserService userService;

        @Autowired
        private AssociationService associationService;

        @Override
        public void run(String... args) throws Exception {
            // Create and save a User
            User user = User.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .email("john.doe@example.com")
                    .passwordHash("password123") // Ensure to hash the password in a real application
                    .phone("1234567890")
                    .address("123 Main St")
                    .userType(User.UserType.INDIVIDUAL)
                    .profilePicture("url_to_picture")
                    .bio("Hello, I'm John!")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            userService.saveUser (user);

            // Create and save an Association
            Association association = Association.builder()
                    .user(user) // Link the association to the user created above
                    .name("Example Association")
                    .email("association@example.com")
                    .password("password123") // Ensure to hash the password in a real application
                    .phone("0987654321")
                    .address("456 Another St")
                    .description("We help the community.")
                    .foundationDate(LocalDate.of(2023, 1, 1))
                    .category("Charity")
                    .logoUrl("url_to_logo")
                    .build();

            associationService.saveAssociation(association);
        }
    }
    */

}