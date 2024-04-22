package com.ksawio.fitnessapi.controllers;

import com.ksawio.fitnessapi.config.PostgreSQLContainerConfiguration;
import com.ksawio.fitnessapi.dto.ExerciseDto;
import com.ksawio.fitnessapi.entities.BodyPart;
import com.ksawio.fitnessapi.entities.Exercise;
import com.ksawio.fitnessapi.repositories.BodyPartRepository;
import com.ksawio.fitnessapi.repositories.ExerciseRepository;
import com.ksawio.fitnessapi.test_utils.LoadTestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(PostgreSQLContainerConfiguration.class)
class ExerciseControllerTest {
    @Autowired
    ExerciseRepository exerciseRepository;
    @Autowired
    BodyPartRepository bodyPartRepository;

    @Autowired
    TestRestTemplate restTemplate;

    private List<Exercise> exercises;

    @BeforeEach
    @Transactional
    void setUp() throws IOException {
        exercises = LoadTestData.loadListData("exercises-body-parts.json", Exercise.class);
        exerciseRepository.saveAll(exercises);

        // connect exercises to body parts in many-to-many relationship
        for (var exercise : exercises) {
            for (var bodyPart : exercise.getBodyParts()) {
                var exercisesInside = bodyPart.getExercises();
                if (exercisesInside == null) {
                    exercisesInside = new HashSet<>();
                    bodyPart.setExercises(exercisesInside);
                }
                exercisesInside.add(exercise);

                bodyPartRepository.save(bodyPart);
            }
        }
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
            var url = String.format("/api/exercise/id/%s", exercise.getId());
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

    @Test
    @Transactional
    void shouldReturnMultipleByBodyPartId() {
        var bodyPart = bodyPartRepository.findAll().get(0);
        var exercise = exerciseRepository.findAll().get(1);

        exercise.getBodyParts().add(bodyPart);
        bodyPart.getExercises().add(exercise);

        bodyPartRepository.save(bodyPart);
        exerciseRepository.save(exercise);

        bodyPart = bodyPartRepository.findById(bodyPart.getId()).orElseThrow(() -> new RuntimeException("BodyPart not found"));
        exercise = exerciseRepository.findById(exercise.getId()).orElseThrow(() -> new RuntimeException("Exercise not found"));

        assertThat(bodyPart.getExercises().toArray()).contains(exercise);
        assertThat(exercise.getBodyParts().toArray()).contains(bodyPart);
    }

    @Test
    void shouldReturnAllById() {
        final var expected = new ArrayList<ExerciseDto>();
        for (var exercise : exercises) {
            expected.add(ExerciseDto.createFromExercise(exercise));
            final var ids = expected.stream().map(ExerciseDto::getId).map(Object::toString).collect(Collectors.joining(","));
            final var url = String.format("/api/exercise/ids/%s", ids);

            var response = restTemplate.getForObject(url, ExerciseDto[].class);
            assertThat(response).isEqualTo(expected.toArray());
        }
    }
}