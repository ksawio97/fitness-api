package com.ksawio.fitnessapi.services;

import com.ksawio.fitnessapi.entities.BodyPart;
import com.ksawio.fitnessapi.repositories.BodyPartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BodyPartServiceImpl implements BodyPartService {
    private final BodyPartRepository repository;

    public BodyPartServiceImpl(final BodyPartRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<BodyPart> findAll() {
        return repository.findAll();
    }
}
