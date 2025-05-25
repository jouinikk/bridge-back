package com.example.cars.users;

import com.example.cars.Models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRespository extends JpaRepository<User,Integer> {
}
