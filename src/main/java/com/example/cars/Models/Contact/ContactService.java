package com.example.cars.Models.Contact;

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
