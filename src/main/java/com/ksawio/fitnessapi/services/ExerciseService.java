package com.ksawio.fitnessapi.services;


import com.ksawio.fitnessapi.dto.ExerciseDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ExerciseService {
    Optional<ExerciseDto> findById(Long id);
    List<ExerciseDto> findAllById(Set<Long> id);
    List<ExerciseDto> findAll();

    List<ExerciseDto> findByBodyPartId(Long id);
}
