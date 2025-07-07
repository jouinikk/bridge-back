package com.example.cars.restcontrollers;
import com.example.cars.entities.Competition;
import com.example.cars.services.CompetitionServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Api(value ="Competition Controller", description = "")
@RestController
@RequestMapping("/Competition")
public class CompetitionController {

    private CompetitionServiceImpl competitionService;

    // Constructor injection
    public CompetitionController(CompetitionServiceImpl competitionService) {
        this.competitionService = competitionService;
    }
    @ApiOperation(value = "Add a new competition", response = Competition.class)
    @PostMapping
    public Competition addCompetition(@RequestBody Competition competition) {
        competition.setDateImportation(LocalDateTime.now());
        competition.setLastModified(LocalDateTime.now());
        return competitionService.createCompetition(competition);
    }

    @ApiOperation(value = "Get all Competitions", response = List.class)
    @GetMapping
    public List<Competition> getAllCompetitions() {
        return competitionService.getAllCompetitions();
    }

    @ApiOperation(value = "Get a competition by Id", response = Competition.class)
    @GetMapping("/{id}")
    public Optional<Competition> getCompetitionById(@PathVariable long id) {
        return competitionService.getCompetitionById(id);
    }


    @ApiOperation(value = "Update an existing competition", response = Competition.class)
    @PutMapping("/{id}")
    public Competition updateCompetition(@PathVariable Long id, @RequestBody Competition competition) {
        competition.setId(id);
        competition.setLastModified(LocalDateTime.now());
        return competitionService.edit(id, competition);
    }

    @ApiOperation(value = "Delete a competition by ID")
    @DeleteMapping("/{id}")
    public void deleteCompetition(@PathVariable Long id) {
        competitionService.deleteCompetition(id);
    }

}