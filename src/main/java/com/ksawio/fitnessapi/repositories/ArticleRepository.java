package com.ksawio.fitnessapi.repositories;

import com.ksawio.fitnessapi.dto.ArticleDto;
import com.ksawio.fitnessapi.entities.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("SELECT new com.ksawio.fitnessapi.dto.ArticleDto(a.id, a.title, a.author, a.publishDate) FROM Article a order by a.publishDate DESC")
    List<ArticleDto> findLast(Pageable pageable);
}
