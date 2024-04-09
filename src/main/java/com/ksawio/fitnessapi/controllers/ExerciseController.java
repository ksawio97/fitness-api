package com.ksawio.fitnessapi.controllers;

import com.ksawio.fitnessapi.dto.ExerciseDto;
import com.ksawio.fitnessapi.services.ExerciseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/exercise")
public class ExerciseController {
    private final ExerciseService exerciseService;
    public ExerciseController(final ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseDto> findById(@PathVariable Long id) {
        Optional<ExerciseDto> retrievedExercise = exerciseService.findById(id);

        return retrievedExercise.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ExerciseDto>> findAll() {
        return ResponseEntity.ok(exerciseService.findAll());
    }

    @GetMapping("/body-part/{id}")
    public ResponseEntity<List<ExerciseDto>> findByBodyPartId(@PathVariable Long id) {
        return ResponseEntity.ok(exerciseService.findByBodyPartId(id));
    }
}
