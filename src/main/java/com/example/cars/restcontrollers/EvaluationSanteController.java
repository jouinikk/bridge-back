package com.example.cars.restcontrollers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.cars.entities.EvaluationSante;
import com.example.cars.services.EvaluationSanteService;

import java.util.List;

@RestController
@RequestMapping("/api/evaluations")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

public class EvaluationSanteController {

    private final EvaluationSanteService evaluationService;

    @PostMapping
    public EvaluationSante add(@RequestBody EvaluationSante evaluation) {
        return evaluationService.addEvaluation(evaluation);
    }

    @GetMapping
    public List<EvaluationSante> getAll() {
        return evaluationService.getAllEvaluations();
    }

    @GetMapping("/{id}")
    public EvaluationSante getById(@PathVariable Long id) {
        return evaluationService.getEvaluationById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        evaluationService.deleteEvaluation(id);
    }
}