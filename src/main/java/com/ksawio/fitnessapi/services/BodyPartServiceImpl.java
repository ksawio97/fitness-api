package com.ksawio.fitnessapi.services;

import com.ksawio.fitnessapi.dto.BodyPartDto;
import com.ksawio.fitnessapi.repositories.BodyPartRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BodyPartServiceImpl implements BodyPartService {
    private final BodyPartRepository repository;

    public BodyPartServiceImpl(final BodyPartRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<BodyPartDto> findAll() {
        return repository.findAll().stream().map(BodyPartDto::createFromBodyPart).toList();
    }

    @Override
    public Optional<BodyPartDto> findByName(String name) {
        final var bodyPart = repository.findByName(name, PageRequest.of(0, 1));
        return bodyPart.get().findFirst();
    }

    @Override
    public Optional<BodyPartDto> findById(Long id) {
        final var found = repository.findById(id);
        return found.map(BodyPartDto::createFromBodyPart);
    }
}
