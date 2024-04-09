package com.ksawio.fitnessapi.controllers;

import com.ksawio.fitnessapi.dto.ArticleDto;
import com.ksawio.fitnessapi.entities.Article;
import com.ksawio.fitnessapi.services.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
    private final ArticleService service;

    public ArticleController(final ArticleService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ArticleDto>> findLast() {
        return ResponseEntity.ok(service.findLast());
    }

    @GetMapping("/{limit}")
    public ResponseEntity<List<ArticleDto>> findLastLimit(@PathVariable int limit) {
        return ResponseEntity.ok(service.findLast(limit));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Article> findLastLimit(@PathVariable long id) {
        var retrievedArticle = service.findById(id);

        return retrievedArticle.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
