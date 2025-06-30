package com.scaler.bookmyshow.services;

import com.scaler.bookmyshow.models.User;
import com.scaler.bookmyshow.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    public User signUp(String email, String password) {
        // TODO: Check if this user already exists

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        return userRepository.save(user);
    }
}
