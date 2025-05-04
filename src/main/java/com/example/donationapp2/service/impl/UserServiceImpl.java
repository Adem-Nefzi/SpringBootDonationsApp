package com.example.donationapp2.service.impl;

import com.example.donationapp2.models.User;
import com.example.donationapp2.repositories.UserRepository;
import com.example.donationapp2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);  // Handle not found situation as needed
    }
    public User getUserByUserType(User.UserType userType) {return userRepository.findByUserType(userType);}
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
