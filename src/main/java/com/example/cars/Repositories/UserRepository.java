package com.example.cars.Repositories;
import com.example.cars.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
    @Override
    Optional<User> findById(Integer integer);
    List<User> findAll();

}

