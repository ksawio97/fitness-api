package com.ksawio.fitnessapi.controllers;

import com.ksawio.fitnessapi.dto.ExerciseDto;
import com.ksawio.fitnessapi.entities.BodyPart;
import com.ksawio.fitnessapi.entities.Exercise;
import com.ksawio.fitnessapi.repositories.ExerciseRepository;
import com.ksawio.fitnessapi.test_utils.LoadTestData;
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
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExerciseControllerTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16.2-alpine");

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    TestRestTemplate restTemplate;

    private List<Exercise> exercises;

    @BeforeEach
    void setUp() throws IOException {
        exercises = LoadTestData.loadListData("exercises.json", Exercise.class);
    }

    @Test
    void shouldReturnAll() {
        var response = restTemplate.getForObject("/exercise", ExerciseDto[].class);
        assertThat(response.length).isEqualTo(0);

        exerciseRepository.saveAll(exercises);

        response = restTemplate.getForObject("/exercise", ExerciseDto[].class);
        assertThat(response.length).isEqualTo(exercises.size());

        var retrieved = Arrays.stream(response).sorted((o1, o2) -> o1.getId() < o2.getId() ? -1 : 1).toList();
        for (int i = 0; i < retrieved.size(); i++) {
            assertThat(retrieved.get(i)).isEqualTo(exercises.get(i));
        }
    }

    @Test
    void shouldReturnById() {
        exerciseRepository.saveAll(exercises);

        for (Exercise exercise : exercises) {
            var url = String.format("/exercise/%s", exercise.getId());
            var response = restTemplate.getForObject(url, Exercise.class);
            assertThat(response).isEqualTo(exercise);
        }
    }

    @Test
    void shouldReturnByBodyPartId() {
        exerciseRepository.saveAll(exercises);
        // check for valid ones, with one result
        for (Exercise exercise : exercises) {
            for (BodyPart bodyPart : exercise.getBodyParts()) {
                var url = String.format("/exercise/bodyPart/%s", bodyPart.getId());
                var response = restTemplate.getForObject(url, ExerciseDto[].class);

                assertThat(response.length).isEqualTo(1);
                assertThat(response[0]).isEqualTo(exercise);
            }
        }

        exerciseRepository.deleteAll();

        // requires first exercise to have at least 1 body part assigned
        var bodyPart = exercises.getFirst().getBodyParts().stream().toList().getFirst();
        var added = Exercise.builder().title("Test").bodyParts(Set.of(bodyPart)).build();
        exerciseRepository.save(added);

        var url = String.format("/exercise/bodyPart/%s", bodyPart.getId());
        var response = restTemplate.getForObject(url, ExerciseDto[].class);

        assertThat(response.length).isEqualTo(2);
        // order doesn't matter
        assertThat(response[0]).isIn(exercises.getFirst(), added);
        assertThat(response[1]).isIn(exercises.getFirst(), added);
    }
}