package com.ksawio.fitnessapi.controllers;

import com.ksawio.fitnessapi.entities.Article;
import com.ksawio.fitnessapi.services.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService service;

    public ArticleController(final ArticleService service) {
        this.service = service;
    }

    // TODO change so this endpoint only returns basic data
    @GetMapping
    public ResponseEntity<List<Article>> findLast() {
        return ResponseEntity.ok(service.findLast());
    }

    // TODO change so this endpoint only returns basic data
    @GetMapping("/{limit}")
    public ResponseEntity<List<Article>> findLastLimit(@PathVariable int limit) {
        return ResponseEntity.ok(service.findLast(limit));
    }

    // TODO add endpoint witch will get article with full data about it
}
