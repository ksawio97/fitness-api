package com.ksawio.fitnessapi.services;


import com.ksawio.fitnessapi.entities.Exercise;

import java.util.List;
import java.util.Optional;

public interface ExerciseService {
    Optional<Exercise> findById(Long id);
    List<Exercise> findAll();
}
