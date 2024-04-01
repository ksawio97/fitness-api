package com.ksawio.fitnessapi.controllers;

import com.ksawio.fitnessapi.entities.Article;
import com.ksawio.fitnessapi.services.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService service;

    public ArticleController(final ArticleService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Article>> findLast() {
        return ResponseEntity.ok(service.findLast());
    }

    @GetMapping("/{limit}")
    public ResponseEntity<List<Article>> findLastLimit(@PathVariable int limit) {
        return ResponseEntity.ok(service.findLast(limit));
    }
}
