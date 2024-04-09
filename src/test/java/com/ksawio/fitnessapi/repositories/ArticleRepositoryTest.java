package com.ksawio.fitnessapi.repositories;

import com.ksawio.fitnessapi.dto.ArticleDto;
import com.ksawio.fitnessapi.entities.Article;
import com.ksawio.fitnessapi.test_utils.LoadTestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace =  AutoConfigureTestDatabase.Replace.NONE)
class ArticleRepositoryTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16.2-alpine");

    private List<Article> articles;

    @Autowired
    ArticleRepository articleRepository;

    @Test
    void connectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

    @BeforeEach
    void setUp() throws IOException {
        articles = LoadTestData.loadListData("articles.json", Article.class);

        articleRepository.saveAll(articles);
    }

    @AfterEach
    void deleteAll() {
        articleRepository.deleteAll();
    }

    @Test
    void shouldReturnLastArticle() {
        var result = articleRepository.findLast(PageRequest.of(0, 1));
        assertThat(result.size()).isEqualTo(1);

        final var lastArticle = articles.stream().min((o1, o2) -> {
            if (o1.getPublishDate().equals(o2.getPublishDate()))
                return 0;
            return o1.getPublishDate().before(o2.getPublishDate()) ? 1 : -1;
        });
        if (lastArticle.isEmpty()) {
            fail("lastArticle wasn't found in test class variable");
        }
        assertThat(result.getFirst()).isEqualTo(ArticleDto.createFromArticle(lastArticle.get()));
    }

    @Test
    void shouldReturnLastArticles() {
        final var result = articleRepository.findLast(Pageable.unpaged());
        assertThat(result.size()).isEqualTo(articles.size());

        final var lastArticles = articles.stream().sorted((o1, o2) -> {
            if (o1.getPublishDate().equals(o2.getPublishDate()))
                return 0;
            return o1.getPublishDate().before(o2.getPublishDate()) ? 1 : -1;
        }).toList();

        for (int i = 0; i < result.size(); i++) {
            assertThat(result.get(i)).isEqualTo(ArticleDto.createFromArticle(lastArticles.get(i)));
        }
    }
}