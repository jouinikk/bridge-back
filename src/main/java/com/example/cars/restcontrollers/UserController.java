package com.example.cars.restcontrollers;

import com.example.cars.dto.AdddUserDTO;
import com.example.cars.dto.PasswordUpdateRequest;
import com.example.cars.entities.User;
import com.example.cars.services.CoachService;
import com.example.cars.services.NageurService;
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
    private final CoachService coachService;
    private final NageurService nageurService;
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

        if(updatedUser.getRole()!=existingUser.getRole()) {
            if (updatedUser.getRole().equals("COACH")) {
                coachService.addUserAsCoach(updatedUser);
            } else if (updatedUser.getRole().equals("SWIMMER")) {
                nageurService.addUserAsSwimmer(updatedUser);
            }
        } else {
                // Handle role-specific fields
                if ("COACH".equals(updatedUser.getRole())) {
                    existingUser.setSpecialite(updatedUser.getSpecialite());
                    existingUser.setAnneeExperience(updatedUser.getAnneeExperience());
                    existingUser.setNiveau(null); // Clear Swimmer-specific field
                } else if ("SWIMMER".equals(updatedUser.getRole())) {
                    existingUser.setNiveau(updatedUser.getNiveau());
                    existingUser.setSpecialite(null); // Clear Coach-specific fields
                    existingUser.setAnneeExperience(0);
                } else if ("ADMIN".equals(updatedUser.getRole())) {
                    existingUser.setSpecialite(null);
                    existingUser.setAnneeExperience(0);
                    existingUser.setNiveau(null);
                }
        }

            // Update common fields
            existingUser.setId(id); // Ensure ID consistency
            existingUser.setName(updatedUser.getName());
            existingUser.setPrenom(updatedUser.getPrenom());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setRole(updatedUser.getRole());
            existingUser.setLocked(updatedUser.isLocked());
            existingUser.setTelephone(updatedUser.getTelephone());


            // Password is not updated in this endpoint
            // existingUser.setPassword(updatedUser.getPassword()); // Uncomment if password update is allowed

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


    @PostMapping("/addUser")
    public ResponseEntity<Void> addUser(@RequestBody User user) {
        return service.addUser(user)!= null ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}