// package com.example.cars.services;

// import com.example.cars.entities.Piscine;

// import java.util.List;

// public interface IPiscineService {
//     Piscine addPiscine(Piscine piscine);
//     List<Piscine> getAllPiscines();
//     Piscine getPiscineById(Long id);
//     Piscine updatePiscine(Piscine piscine);
//     void deletePiscine(Long id);
//     List<Piscine> getPiscinesByVille(String ville);
//     List<Piscine> searchPiscines(String keyword);
// }


package com.example.cars.services;

import com.example.cars.entities.Piscine;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPiscineService {
    Piscine addPiscine(Piscine piscine);
    List<Piscine> getAllPiscines();
    Piscine getPiscineById(Long id);
    Piscine updatePiscine(Piscine piscine);
    void deletePiscine(Long id);
    List<Piscine> getPiscinesByVille(String ville);
    List<Piscine> searchPiscines(String keyword);
    void importPiscinesFromExcel(MultipartFile file);
}