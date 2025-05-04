package com.example.donationapp2.service;

import com.example.donationapp2.models.User;
import java.util.List;


public interface UserService {
    User saveUser(User user);
    List<User> getAllUsers();
    User getUserById(Long id);
    void deleteUser(Long id);
    User getUserByUserType(User.UserType userType);
    User getUserByEmail(String email);
}
