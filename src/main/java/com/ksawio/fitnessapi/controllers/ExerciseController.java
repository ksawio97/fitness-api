package com.ksawio.fitnessapi.controllers;

import com.ksawio.fitnessapi.entities.Exercise;
import com.ksawio.fitnessapi.services.ExerciseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import java.util.Optional;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {
    private final ExerciseService exerciseService;
    public ExerciseController(final ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> findById(@PathVariable Long id) {
        Optional<Exercise> retrievedExercise = exerciseService.findById(id);

        return retrievedExercise.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Exercise>> findAll() {
        return ResponseEntity.ok(exerciseService.findAll());
    }

    @GetMapping("/body-part/{id}")
    public ResponseEntity<List<Exercise>> findByBodyPartId(@PathVariable Long id) {
        return ResponseEntity.ok(exerciseService.findByBodyPartId(id));
    }
}
