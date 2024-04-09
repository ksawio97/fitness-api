package com.ksawio.fitnessapi.controllers;

import com.ksawio.fitnessapi.config.PostgreSQLContainerConfiguration;
import com.ksawio.fitnessapi.dto.ExerciseDto;
import com.ksawio.fitnessapi.entities.BodyPart;
import com.ksawio.fitnessapi.entities.Exercise;
import com.ksawio.fitnessapi.repositories.ExerciseRepository;
import com.ksawio.fitnessapi.test_utils.LoadTestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(PostgreSQLContainerConfiguration.class)
class ExerciseControllerTest {
    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    TestRestTemplate restTemplate;

    private List<Exercise> exercises;

    @BeforeEach
    void setUp() throws IOException {
        exercises = LoadTestData.loadListData("exercises-body-parts.json", Exercise.class);

        exerciseRepository.saveAll(exercises);
    }

    @AfterEach
    void deleteAll() {
        exerciseRepository.deleteAll();
    }

    @Test
    void shouldReturnAll() {
        var response = restTemplate.getForObject("/api/exercise", ExerciseDto[].class);
        assertThat(response.length).isEqualTo(exercises.size());
        final var exercisesDto = exercises.stream().map(ExerciseDto::createFromExercise).toArray();
        for (ExerciseDto exerciseDto : response) {
            assertThat(exerciseDto).isIn(exercisesDto);
        }

        exerciseRepository.deleteAll(exercises);

        response = restTemplate.getForObject("/api/exercise", ExerciseDto[].class);
        assertThat(response.length).isEqualTo(0);
    }

    @Test
    void shouldReturnById() {
        for (Exercise exercise : exercises) {
            var url = String.format("/api/exercise/%s", exercise.getId());
            var response = restTemplate.getForObject(url, ExerciseDto.class);
            assertThat(response).isEqualTo(ExerciseDto.createFromExercise(exercise));
        }
    }

    @Test
    void shouldReturnByBodyPartId() {
        // check for valid ones, with one result
        for (Exercise exercise : exercises) {
            for (BodyPart bodyPart : exercise.getBodyParts()) {
                var url = String.format("/api/exercise/body-part/%s", bodyPart.getId());
                var response = restTemplate.getForObject(url, ExerciseDto[].class);

                assertThat(response.length).isEqualTo(1);
                assertThat(response[0]).isEqualTo(ExerciseDto.createFromExercise(exercise));
            }
        }
    }
}