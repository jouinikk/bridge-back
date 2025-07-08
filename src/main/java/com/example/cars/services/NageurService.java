package com.example.cars.services;

import com.example.cars.entities.Nageur;
import com.example.cars.Repositories.NageurRepository;
import com.example.cars.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NageurService implements INageurService {

    private final NageurRepository nageurRepository;
    private final GroupeService groupeService;

    @Override
    public List<Nageur> getAllNageurs() {
        return nageurRepository.findAll();
    }

    @Override
    public Nageur getNageurById(Long id) {
        return nageurRepository.findById(id).orElse(null);
    }

    @Override
    public List<Nageur> getNageursByNiveau(String niveau) {
        return nageurRepository.findByLevel(niveau);
    }

    @Override
    public List<Nageur> getNageursByGroupeId(Long groupeId) {
        return nageurRepository.findByGroupesId(groupeId);
    }

    @Override
    public Nageur addNageur(Nageur nageur) {
        return nageurRepository.save(nageur);
    }

    @Override
    public Nageur updateNageur(Nageur nageur) {
        return nageurRepository.save(nageur);
    }

    @Override
    public void deleteNageur(Long id) {
        nageurRepository.deleteById(id);
    }

    public User addUserAsSwimmer(User user) {
        Nageur nageur = new Nageur();

        // Correspondance des champs
        nageur.setLevel(user.getNiveau());             // si dans Nageur c'est 'level' (en anglais)
        nageur.setFirstName(user.getName());           // ou user.getPrenom(), à confirmer
        nageur.setLastName(user.getPrenom());          // inversion possible, à ajuster selon les noms réels
        nageur.setEmail(user.getEmail());
        nageur.setPhone(user.getTelephone());

        // Ne pas setter l'id manuellement si c'est auto-généré
        // nageur.setId(user.getId());  // à éviter si @GeneratedValue

                nageurRepository.save(nageur);

        return user;
    }

    public void updateUserAsSwimmer(User user) {
        Nageur swimmer = nageurRepository.findById(Long.parseLong(String.valueOf(user.getId())))
                .orElseThrow(() -> new IllegalArgumentException("Swimmer with ID " + user.getId() + " not found"));

        swimmer.setNiveau(user.getNiveau());
        swimmer.setNom(user.getName());
        swimmer.setPrenom(user.getPrenom());
        swimmer.setEmail(user.getEmail());
        swimmer.setTelephone(user.getTelephone());

        nageurRepository.save(swimmer);
    }
}