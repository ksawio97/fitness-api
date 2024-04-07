package com.ksawio.fitnessapi.services;

import com.ksawio.fitnessapi.dto.ExerciseDto;
import com.ksawio.fitnessapi.repositories.ExerciseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseServiceImpl implements ExerciseService {
    private final ExerciseRepository repository;

    public ExerciseServiceImpl(final ExerciseRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<ExerciseDto> findById(Long id) {
        final var found = repository.findById(id);
        return found.map(ExerciseDto::createFromExercise);
    }

    @Override
    public List<ExerciseDto> findAll() {
        return repository.findAll().stream().map(ExerciseDto::createFromExercise).toList();
    }

    @Override
    public List<ExerciseDto> findByBodyPartId(Long id) {
        return repository.findByBodyPartId(id).stream().map(ExerciseDto::createFromExercise).toList();
    }
}
