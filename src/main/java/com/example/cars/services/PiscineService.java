// package com.example.cars.services;

// import com.example.cars.entities.Piscine;
// import com.example.cars.Repositories.LigneEauRepository;
// import com.example.cars.Repositories.PiscineRepository;
// import lombok.RequiredArgsConstructor;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// @RequiredArgsConstructor
// public class PiscineService implements IPiscineService {

//     private final PiscineRepository piscineRepository;
//     private final LigneEauRepository ligneEauRepository;


//     // @Override
//     // public Piscine addPiscine(Piscine piscine) {
//     //     return piscineRepository.save(piscine);
//     // }

//     @Override
//     public Piscine addPiscine(Piscine piscine) {
//         if (piscine.getLignesEau() != null) {
//             // Save the piscine first to get an ID
//             Piscine savedPiscine = piscineRepository.save(piscine);

//             // Then save all lignesEau with the proper relationship
//             piscine.getLignesEau().forEach(ligne -> {
//                 ligne.setPiscine(savedPiscine);
//                 ligneEauRepository.save(ligne);
//             });

//             return piscineRepository.findById(savedPiscine.getId()).orElse(savedPiscine);
//         }
//         return piscineRepository.save(piscine);
//     }
    
//     @Override
//     public List<Piscine> getAllPiscines() {
//         return piscineRepository.findAll();
//     }

//     @Override
//     public Piscine getPiscineById(Long id) {
//         return piscineRepository.findById(id).orElse(null);
//     }

//     @Override
//     public Piscine updatePiscine(Piscine piscine) {
//         return piscineRepository.save(piscine);
//     }

//     @Override
//     public void deletePiscine(Long id) {
//         piscineRepository.deleteById(id);
//     }

//     @Override
//     public List<Piscine> getPiscinesByVille(String ville) {
//         return piscineRepository.findByVille(ville);
//     }

//     @Override
//     public List<Piscine> searchPiscines(String keyword) {
//         return piscineRepository.findByNomContaining(keyword);
//     }
// }



package com.example.cars.services;

import com.example.cars.entities.Piscine;
import com.example.cars.Repositories.LigneEauRepository;
import com.example.cars.Repositories.PiscineRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PiscineService implements IPiscineService {

    private final PiscineRepository piscineRepository;
    private final LigneEauRepository ligneEauRepository;

    @Override
    public Piscine addPiscine(Piscine piscine) {
        if (piscine.getLignesEau() != null) {
            Piscine savedPiscine = piscineRepository.save(piscine);
            piscine.getLignesEau().forEach(ligne -> {
                ligne.setPiscine(savedPiscine);
                ligneEauRepository.save(ligne);
            });
            return piscineRepository.findById(savedPiscine.getId()).orElse(savedPiscine);
        }
        return piscineRepository.save(piscine);
    }

    @Override
    public void importPiscinesFromExcel(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            // Skip header row
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            List<Piscine> piscines = new ArrayList<>();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Piscine piscine = new Piscine();

                piscine.setNom(getCellValue(row.getCell(0)));
                piscine.setAdresse(getCellValue(row.getCell(1)));
                piscine.setVille(getCellValue(row.getCell(2)));
                piscine.setCodePostal(getCellValue(row.getCell(3)));
                piscine.setTelephone(getCellValue(row.getCell(4)));
                piscine.setEmail(getCellValue(row.getCell(5)));

                // If you have geolocation data in the Excel
                if (row.getCell(6) != null && row.getCell(7) != null) {
                    piscine.setLatitude(row.getCell(6).getNumericCellValue());
                    piscine.setLongitude(row.getCell(7).getNumericCellValue());
                }

                piscines.add(piscine);
            }

            piscineRepository.saveAll(piscines);
        } catch (IOException e) {
            throw new RuntimeException("Failed to import Excel file: " + e.getMessage());
        }
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return null;
        }
    }

    @Override
    public List<Piscine> getAllPiscines() {
        return piscineRepository.findAll();
    }


    @Override
    public Piscine getPiscineById(Long id) {
        return piscineRepository.findById(id).orElse(null);
    }

    @Override
    public Piscine updatePiscine(Piscine piscine) {
        return piscineRepository.save(piscine);
    }

    @Override
    public void deletePiscine(Long id) {
        piscineRepository.deleteById(id);
    }

    @Override
    public List<Piscine> getPiscinesByVille(String ville) {
        return piscineRepository.findByVille(ville);
    }

    @Override
    public List<Piscine> searchPiscines(String keyword) {
        return piscineRepository.findByNomContaining(keyword);
    }

    // ... rest of your existing methods ...
}