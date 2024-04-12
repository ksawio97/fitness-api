package com.ksawio.fitnessapi.repositories;

import com.ksawio.fitnessapi.config.PostgreSQLContainerConfiguration;
import com.ksawio.fitnessapi.dto.BodyPartDto;
import com.ksawio.fitnessapi.entities.BodyPart;
import com.ksawio.fitnessapi.test_utils.LoadTestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace =  AutoConfigureTestDatabase.Replace.NONE)
@Import(PostgreSQLContainerConfiguration.class)
class BodyPartRepositoryTest {
    private List<BodyPart> bodyParts;

    @Autowired
    BodyPartRepository bodyPartRepository;

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
    void shouldReturnEmpty() {
        // assuming this name is not in body parts
        final var name = "dsadasdsadjbsadjhakxbjkadbsabwjhe";
        var result = bodyPartRepository.findByName(name, PageRequest.of(0, 1));
        assertThat(result.getTotalElements()).isEqualTo(0);
    }

    @Test
    void shouldReturnBodyPart() {
        // we assume this name is not in body parts
        final var name = bodyParts.getFirst().getName();
        var result = bodyPartRepository.findByName(name, PageRequest.of(0, 1));
        assertThat(result.iterator().next()).isEqualTo(BodyPartDto.createFromBodyPart(bodyParts.getFirst()));
    }
}