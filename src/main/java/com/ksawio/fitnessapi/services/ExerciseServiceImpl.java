package com.ksawio.fitnessapi.services;

import com.ksawio.fitnessapi.entities.Exercise;
import com.ksawio.fitnessapi.repositories.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Optional<Exercise> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Exercise> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Exercise> findByBodyPartId(Long id) {
        return repository.findByBodyPartId(id);
    }
}
