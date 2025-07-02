package com.example.cars.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.cars.entities.EvaluationSante;
import com.example.cars.Repositories.EvaluationSanteRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationSanteService implements IEvaluationSanteService {
    private final EvaluationSanteRepository evaluationRepo;

    public EvaluationSante addEvaluation(EvaluationSante evaluation) {
        return evaluationRepo.save(evaluation);
    }

    public List<EvaluationSante> getAllEvaluations() {
        return evaluationRepo.findAll();
    }

    public EvaluationSante getEvaluationById(Long id) {
        return evaluationRepo.findById(id).orElse(null);
    }

    public void deleteEvaluation(Long id) {
        evaluationRepo.deleteById(id);
    }
}
