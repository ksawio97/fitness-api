package com.ksawio.fitnessapi.controllers;


import com.ksawio.fitnessapi.entities.BodyPart;
import com.ksawio.fitnessapi.services.BodyPartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<List<BodyPart>> findAll() {
        return ResponseEntity.ok(bodyPartService.findAll());
    }
}
