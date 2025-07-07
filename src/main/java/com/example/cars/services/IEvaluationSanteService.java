package com.example.cars.services;
import com.example.cars.entities.EvaluationSante;
import java.util.List;

public interface IEvaluationSanteService {
    EvaluationSante addEvaluation(EvaluationSante evaluation);
    List<EvaluationSante> getAllEvaluations();
    EvaluationSante getEvaluationById(Long id);
    void deleteEvaluation(Long id);
}

