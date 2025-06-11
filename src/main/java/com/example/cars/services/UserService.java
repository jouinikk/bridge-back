package com.example.cars.services;

import com.example.cars.Repositories.UserRepository;
import com.example.cars.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> users(){
        return userRepository.findAll();
    }

    public User getUserById(int id) throws NoSuchElementException {
        return  userRepository.findById(id).get();
    }

    public User updateUser(User user){
        return userRepository.save(user);
    }

    public ResponseEntity<?> updatePassword(int userId, String currentPassword, String newPassword, String confirmPassword) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = optionalUser.get();

        // 1. Check if current password matches
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Current password is incorrect");
        }

        // 2. Check if new and confirm password match
        if (!newPassword.equals(confirmPassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("New passwords do not match");
        }

        // 3. Encode new password and save
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedNewPassword);
        userRepository.save(user);

        return ResponseEntity.ok("Password updated successfully");
    }


    public User toggleLock(int userId) {
        User optionalUser = userRepository.findById(userId).get();

        optionalUser.setLocked(!optionalUser.isLocked());
        return userRepository.save(optionalUser);
    }
}
