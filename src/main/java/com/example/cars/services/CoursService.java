package com.example.cars.services;

import com.example.cars.Repositories.CourRepository;
import com.example.cars.entities.Cours;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoursService {

    private final CourRepository repository;

    public Cours addCours(Cours c){
        return repository.save(c);
    }

    public List<Cours> getCours(){
        return repository.findAll();
    }

    public Cours getCoursById(int id){
        return repository.findById(id).get();
    }

    public void deleteCoursById(int id){
        repository.deleteById(id);
    }

    public void updateCours(Cours c){
        repository.save(c);
    }
}
