package com.ksawio.fitnessapi.repositories;

import com.ksawio.fitnessapi.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}
