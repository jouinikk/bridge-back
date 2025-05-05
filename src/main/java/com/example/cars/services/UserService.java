package com.example.cars.services;

import com.example.cars.Repositories.UserRepository;
import com.example.cars.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor

public class UserService {
    private final UserRepository userRepository;

    public List<User> users(){
        return userRepository.findAll();
    }

    public User getUserById(int id) throws NoSuchElementException {
        return  userRepository.findById(id).get();
    }

}
