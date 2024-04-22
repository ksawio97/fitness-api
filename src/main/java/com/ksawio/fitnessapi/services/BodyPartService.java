package com.ksawio.fitnessapi.services;

import com.ksawio.fitnessapi.dto.BodyPartDto;

import java.util.List;
import java.util.Optional;

public interface BodyPartService {
    List<BodyPartDto> findAll();

    Optional<BodyPartDto> findByName(String name);

    Optional<BodyPartDto> findById(Long id);
}
