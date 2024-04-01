package com.ksawio.fitnessapi.repositories;

import com.ksawio.fitnessapi.entities.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("SELECT a FROM Article a order by a.publishDate DESC")
    List<Article> findLast(Pageable pageable);
}
