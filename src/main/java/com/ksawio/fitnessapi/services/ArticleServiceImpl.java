package com.ksawio.fitnessapi.services;

import com.ksawio.fitnessapi.dto.ArticleDto;
import com.ksawio.fitnessapi.entities.Article;
import com.ksawio.fitnessapi.repositories.ArticleRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository repository;

    public ArticleServiceImpl(final ArticleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ArticleDto> findLast(int limit) {
        return repository.findLast(PageRequest.of(0, limit));
    }
    @Override
    public List<ArticleDto> findLast() {
        return repository.findLast(Pageable.unpaged());
    }

    @Override
    public Optional<Article> findById(Long id) {
        return repository.findById(id);
    }
}
