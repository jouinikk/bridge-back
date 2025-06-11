package com.example.cars.restcontrollers;

import com.example.cars.dto.PasswordUpdateRequest;
import com.example.cars.entities.User;
import com.example.cars.services.UserService;
import lombok.RequiredArgsConstructor;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cars.entities.Nageur;
import com.example.cars.entities.User;
import com.example.cars.services.UserService;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/")
public class UserController {


    private final UserService service ;



    private final UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.users();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User updatedUser) {
       User existingUser = userService.getUserById(id);

        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }

        // Update fields manually (you can use a mapper if needed)
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setRole(updatedUser.getRole());
        // ... other fields

        User savedUser = userService.updateUser(existingUser);

        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/{userId}/update-password")
    public ResponseEntity<?> updatePassword(
            @PathVariable int userId,
            @RequestBody PasswordUpdateRequest request
    ) {
        return userService.updatePassword(userId, request.getCurrentPassword(), request.getNewPassword(), request.getConfirmPassword());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return ResponseEntity.ok(this.userService.getUserById(id));
    }


        @PutMapping("toggleLock/{id}")
    public ResponseEntity<User> toggleLock(@PathVariable int id) {
        return ResponseEntity.ok(this.userService.toggleLock(id));
    }
}