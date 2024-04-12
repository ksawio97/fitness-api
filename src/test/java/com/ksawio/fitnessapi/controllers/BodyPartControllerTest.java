package com.ksawio.fitnessapi.controllers;

import com.ksawio.fitnessapi.config.PostgreSQLContainerConfiguration;
import com.ksawio.fitnessapi.dto.BodyPartDto;
import com.ksawio.fitnessapi.entities.BodyPart;
import com.ksawio.fitnessapi.repositories.BodyPartRepository;
import com.ksawio.fitnessapi.test_utils.LoadTestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(PostgreSQLContainerConfiguration.class)
class BodyPartControllerTest {
    @Autowired
    BodyPartRepository bodyPartRepository;

    @Autowired
    TestRestTemplate restTemplate;
    private List<BodyPart> bodyParts;

    @BeforeEach
    void setUp() throws IOException {
        bodyParts = LoadTestData.loadListData("body-parts.json", BodyPart.class);

        bodyPartRepository.saveAll(bodyParts);
    }

    @AfterEach
    void deleteAll() {
        bodyPartRepository.deleteAll();
    }

    @Test
    void shouldReturnAllBodyParts() {
        var response = restTemplate.getForObject("/api/body-part", BodyPart[].class);
        assertThat(response.length).isEqualTo(bodyParts.size());

        for (var bodyPart : response) {
            assertThat(bodyPart).isIn(bodyParts);
        }
    }

    @Test
    void shouldReturnByName() {
        final var toFind = bodyParts.getFirst();
        final var url = String.format("/api/body-part/name/%s", toFind.getName());

        var response = restTemplate.getForObject(url, BodyPartDto.class);
        assertThat(response).isEqualTo(BodyPartDto.createFromBodyPart(toFind));
    }

    @Test
    void shouldReturnEmptyByName() {
        // assuming this name is not in body parts
        final var name = "dsadasdsadjbsadjhakxbjkadbsabwjhe";
        final var url = String.format("/api/body-part/name/%s", name);

        var response = restTemplate.getForEntity(url, BodyPartDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}