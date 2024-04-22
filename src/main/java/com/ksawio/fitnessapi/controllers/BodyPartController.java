package com.ksawio.fitnessapi.controllers;


import com.ksawio.fitnessapi.dto.BodyPartDto;
import com.ksawio.fitnessapi.services.BodyPartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/body-part")
public class BodyPartController {
    private final BodyPartService bodyPartService;
    public BodyPartController(final BodyPartService bodyPartService) {
        this.bodyPartService = bodyPartService;
    }

    @GetMapping
    public ResponseEntity<List<BodyPartDto>> findAll() {
        return ResponseEntity.ok(bodyPartService.findAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<BodyPartDto> findById(@PathVariable long id) {
        final var retrievedBodyPart = bodyPartService.findById(id);

        return retrievedBodyPart.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/name/{name}")
    public ResponseEntity<BodyPartDto> findByName(@PathVariable String name) {
        final var bodyPart = bodyPartService.findByName(name);

        return bodyPart.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}