package com.ksawio.fitnessapi.services;

import com.ksawio.fitnessapi.entities.Article;
import com.ksawio.fitnessapi.repositories.ArticleRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository repository;

    public ArticleServiceImpl(final ArticleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Article> findLast(int limit) {
        return repository.findLast(PageRequest.of(0, limit));
    }
    @Override
    public List<Article> findLast() {
        return repository.findLast(Pageable.unpaged());
    }
}
