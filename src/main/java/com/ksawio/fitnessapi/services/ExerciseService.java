package com.ksawio.fitnessapi.services;


import com.ksawio.fitnessapi.dto.ExerciseDto;

import java.util.List;
import java.util.Optional;

public interface ExerciseService {
    Optional<ExerciseDto> findById(Long id);
    List<ExerciseDto> findAll();

    List<ExerciseDto> findByBodyPartId(Long id);
}
