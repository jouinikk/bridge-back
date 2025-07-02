package com.example.cars.services;

import com.example.cars.dto.GroupNameDTO;
import com.example.cars.entities.Groupe;
import com.example.cars.entities.Nageur;
import com.example.cars.Repositories.GroupeRepository;
import com.example.cars.Repositories.NageurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupeService implements IGroupeService {
    
    private final GroupeRepository groupeRepository;
    private final NageurRepository nageurRepository;
    
    @Override
    public List<Groupe> getAllGroupes() {
        return groupeRepository.findAll();
    }
    
    @Override
    public Groupe getGroupeById(Long id) {
        return groupeRepository.findById(id).orElse(null);
    }
    
    @Override
    public List<Groupe> getGroupesByCoachId(Long coachId) {
        return groupeRepository.findByCoachId(coachId);
    }
    
    @Override
    public Groupe addGroupe(Groupe groupe) {
        return groupeRepository.save(groupe);
    }
    
    @Override
    public Groupe updateGroupe(Groupe groupe) {
        return groupeRepository.save(groupe);
    }
    
    @Override
    public void deleteGroupe(Long id) {
        groupeRepository.deleteById(id);
    }
    
    @Override
    public void addNageurToGroupe(Long groupeId, Long nageurId) {
        Groupe groupe = groupeRepository.findById(groupeId).orElse(null);
        Nageur nageur = nageurRepository.findById(nageurId).orElse(null);
        
        if (groupe != null && nageur != null) {
            groupe.getNageurs().add(nageur);
            groupeRepository.save(groupe);
        }
    }
    
    @Override
    public void removeNageurFromGroupe(Long groupeId, Long nageurId) {
        Groupe groupe = groupeRepository.findById(groupeId).orElse(null);
        if (groupe != null) {
            groupe.getNageurs().removeIf(n -> n.getId().equals(nageurId));
            groupeRepository.save(groupe);
        }
    }

    public List<GroupNameDTO> getGroupeNames() {
        List<Groupe> groupes = groupeRepository.findAll();
        List<GroupNameDTO> dtos = new ArrayList<>();
        for (Groupe groupe : groupes) {
            dtos.add(new GroupNameDTO(groupe.getNom(),groupe.getId()));
        }
        return dtos;
    }

}
