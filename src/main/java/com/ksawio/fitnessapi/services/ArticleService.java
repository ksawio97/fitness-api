package com.ksawio.fitnessapi.services;

import com.ksawio.fitnessapi.dto.ArticleDto;
import com.ksawio.fitnessapi.entities.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<ArticleDto> findLast(int limit);
    List<ArticleDto> findLast();
    Optional<Article> findById(Long id);
}
