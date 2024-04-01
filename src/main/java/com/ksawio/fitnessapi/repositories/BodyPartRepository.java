package com.ksawio.fitnessapi.repositories;

import com.ksawio.fitnessapi.entities.BodyPart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BodyPartRepository extends JpaRepository<BodyPart, Long> {
}
