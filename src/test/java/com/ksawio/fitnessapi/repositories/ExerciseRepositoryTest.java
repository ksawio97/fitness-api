package com.ksawio.fitnessapi.repositories;

import com.ksawio.fitnessapi.entities.BodyPart;
import com.ksawio.fitnessapi.entities.Exercise;
import com.ksawio.fitnessapi.test_utils.LoadTestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace =  AutoConfigureTestDatabase.Replace.NONE)
class ExerciseRepositoryTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16.2-alpine");

    private List<Exercise> exercises;

    @Autowired
    ExerciseRepository exerciseRepository;

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
    void shouldReturnWithCorrectId() {
        for (Exercise exercise : exercises) {
            for (BodyPart bodyPart : exercise.getBodyParts()) {
                var results = exerciseRepository.findByBodyPartId(bodyPart.getId());

                assertThat(results.size()).isEqualTo(1);
                assertThat(results.getFirst()).isEqualTo(exercise);
            }
        }
    }
}