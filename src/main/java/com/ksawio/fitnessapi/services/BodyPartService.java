package com.ksawio.fitnessapi.services;

import com.ksawio.fitnessapi.entities.BodyPart;

import java.util.List;

public interface BodyPartService {
    List<BodyPart> findAll();
}
