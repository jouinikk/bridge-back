package com.example.cars.users;

import com.example.cars.Models.user.User;
import com.example.cars.Models.user.UserRepository;
import com.example.cars.Models.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UserRepository repo;

    public List<User> getUsers() {
        return this.repo.findAll();
    }
}
