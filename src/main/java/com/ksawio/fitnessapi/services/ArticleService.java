package com.ksawio.fitnessapi.services;

import com.ksawio.fitnessapi.entities.Article;

import java.util.List;

public interface ArticleService {
    List<Article> findLast(int limit);
    List<Article> findLast();
}
