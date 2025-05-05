package com.example.cars.restcontrollers;

import com.example.cars.services.CoursService;
import com.example.cars.entities.Cours;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cours")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200/")
public class CoursController {

    private final CoursService service;

    @GetMapping
    public List<Cours> getCours(){
        return service.getCours();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cours> getCoursById(@PathVariable int id){
        return ResponseEntity.ok(service.getCoursById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<Cours> addCours(@RequestBody Cours c){
        return ResponseEntity.ok(service.addCours(c));
    }

    @PatchMapping("/update")
    public void updateCours(@RequestBody Cours c){
        service.updateCours(c);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCours(@PathVariable int id){
        service.deleteCoursById(id);
    }
}

