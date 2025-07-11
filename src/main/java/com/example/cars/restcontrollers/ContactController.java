package com.example.cars.restcontrollers;

import com.example.cars.entities.Contact;
import com.example.cars.services.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contact")
public class ContactController {
    private final ContactService service;

    @GetMapping
    public List<Contact> getAll(){
        return service.getAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Contact> addContact(@RequestBody Contact c){
        return ResponseEntity.ok(service.add(c));
    }

}
