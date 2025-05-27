package com.example.cars.restcontrollers;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cars.entities.Nageur;
import com.example.cars.entities.User;
import com.example.cars.services.UserService;


@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/")
public class UserController {

    private final UserService service ;

@GetMapping
    public List<User> getUsers() {    return service.users();}

}