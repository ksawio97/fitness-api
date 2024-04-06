package com.ksawio.fitnessapi.controllers;

import com.ksawio.fitnessapi.entities.Article;
import com.ksawio.fitnessapi.repositories.ArticleRepository;
import com.ksawio.fitnessapi.test_utils.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ArticleControllerTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16.2-alpine");

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    TestRestTemplate restTemplate;
    private List<Article> articles;
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
    }


    @Test
    void shouldReturnEmptyList() {
        var response = restTemplate.getForObject("/article", Article[].class);
        assertThat(response.length).isEqualTo(0);

        response = restTemplate.getForObject("/article/2", Article[].class);
        assertThat(response.length).isEqualTo(0);
    }

    @Test
    void shouldReturnList() {
        articleRepository.saveAll(articles);

        var response = restTemplate.getForObject("/article", Article[].class);
        assertThat(response.length).isEqualTo(articles.size());
        var retrieved = Arrays.stream(response).sorted((o1, o2) -> o1.getId() < o2.getId() ? -1 : 1).toList();

        for (int i = 0; i < retrieved.size(); i++) {
            assertThat(retrieved.get(i)).isEqualTo(articles.get(i));
        }
    }
}