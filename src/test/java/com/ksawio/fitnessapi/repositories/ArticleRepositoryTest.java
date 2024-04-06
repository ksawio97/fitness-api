package com.ksawio.fitnessapi.repositories;

import com.ksawio.fitnessapi.entities.Article;
import com.ksawio.fitnessapi.test_utils.DateUtils;
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
    void setUp() {
        articles = List.of(Article.builder()
                .title("First Article")
                .content("This is the content of the first article.")
                .author("Author One")
                .publishDate(DateUtils.randomDate())
                .build(), Article.builder()
                .title("Second Article")
                .content("This is the content of the second article.")
                .author("Author Two")
                .publishDate(DateUtils.randomDate())
                .build(), Article.builder()
                .title("Third Article")
                .content("This is the content of the third article.")
                .author("Author Three")
                .publishDate(DateUtils.randomDate())
                .build(), Article.builder()
                .title("Fourth Article")
                .content("This is the content of the fourth article.")
                .author("Author Four")
                .publishDate(DateUtils.randomDate())
                .build(), Article.builder()
                .title("Fifth Article")
                .content("This is the content of the fifth article.")
                .author("Author Five")
                .publishDate(DateUtils.randomDate())
                .build());

        articleRepository.saveAll(articles);
    }

    @Test
    void shouldReturnLastArticle() {
        List<Article> result = articleRepository.findLast(PageRequest.of(0, 1));
        assertThat(result.size()).isEqualTo(1);

        final var lastArticle = articles.stream().min((o1, o2) -> {
            if (o1.getPublishDate().equals(o2.getPublishDate()))
                return 0;
            return o1.getPublishDate().before(o2.getPublishDate()) ? 1 : -1;
        });
        if (lastArticle.isEmpty()) {
            fail("lastArticle wasn't found in test class variable");
        }
        assertThat(result.getFirst()).isEqualTo(lastArticle.get());
    }

    @Test
    void shouldReturnLastArticles() {
        final List<Article> result = articleRepository.findLast(Pageable.unpaged());
        assertThat(result.size()).isEqualTo(articles.size());

        final var lastArticles = articles.stream().sorted((o1, o2) -> {
            if (o1.getPublishDate().equals(o2.getPublishDate()))
                return 0;
            return o1.getPublishDate().before(o2.getPublishDate()) ? 1 : -1;
        }).toList();

        for (int i = 0; i < result.size(); i++) {
            assertThat(result.get(i)).isEqualTo(lastArticles.get(i));
        }
    }
}