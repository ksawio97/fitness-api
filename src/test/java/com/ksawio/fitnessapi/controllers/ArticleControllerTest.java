package com.ksawio.fitnessapi.controllers;

import com.ksawio.fitnessapi.dto.ArticleDto;
import com.ksawio.fitnessapi.entities.Article;
import com.ksawio.fitnessapi.repositories.ArticleRepository;
import com.ksawio.fitnessapi.test_utils.LoadTestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
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
    void setUp() throws IOException {
        articles = LoadTestData.loadListData("articles.json", Article.class);

        articleRepository.saveAll(articles);
    }

    @AfterEach
    void deleteAll() {
        articleRepository.deleteAll();
    }

    @Test
    void shouldReturnEmptyList() {
        articleRepository.deleteAll();
        var response = restTemplate.getForObject("/article", ArticleDto[].class);
        assertThat(response.length).isEqualTo(0);

        response = restTemplate.getForObject("/article/2", ArticleDto[].class);
        assertThat(response.length).isEqualTo(0);
    }

    @Test
    void shouldReturnList() {
        var response = restTemplate.getForObject("/article", ArticleDto[].class);
        assertThat(response.length).isEqualTo(articles.size());
        //noinspection ComparatorMethodParameterNotUsed
        var retrieved = Arrays.stream(response).sorted((o1, o2) -> o1.getId() < o2.getId() ? -1 : 1).toList();

        for (int i = 0; i < retrieved.size(); i++) {
            assertThat(retrieved.get(i)).isEqualTo(ArticleDto.createFromArticle(articles.get(i)));
        }
    }

    @Test
    void shouldReturnById() {
        for (var article : articles) {
            var url = String.format("/article/id/%s", article.getId());
            var response = restTemplate.getForObject(url, Article.class);
            assertThat(response).isEqualTo(article);
        }
    }
}