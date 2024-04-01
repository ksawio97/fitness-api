package com.ksawio.fitnessapi.repositories;

import com.ksawio.fitnessapi.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    @Query("SELECT e FROM Exercise e WHERE e.bodyPart.id = :id")
    List<Exercise> findByBodyPartId(@Param("id") Long id);
}
