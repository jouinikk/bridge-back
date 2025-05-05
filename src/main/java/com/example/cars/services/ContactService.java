package com.example.cars.services;

import com.example.cars.entities.Contact;
import com.example.cars.Repositories.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository repository;

    public Contact add(Contact c){
        return repository.save(c);
    }

    public List<Contact> getAll(){
        return repository.findAll();
    }
}
