package com.ksawio.fitnessapi.dto;

import com.ksawio.fitnessapi.entities.BodyPart;
import com.ksawio.fitnessapi.entities.Exercise;
import lombok.*;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BodyPartDto {
    private Long id;
    private String name;
    private Set<Long> exercises;

    public BodyPartDto(BodyPart bodyPart) {
        this.id = bodyPart.getId();
        this.name = bodyPart.getName();
        this.exercises = convertExercises(bodyPart.getExercises());
    }
    private static Set<Long> convertExercises(Set<Exercise> exercises) {
        return Optional.ofNullable(exercises)
                .map(e -> e.stream().map(Exercise::getId).collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
    }

    public static BodyPartDto createFromBodyPart(@NonNull BodyPart bodyPart) {
        return BodyPartDto.builder()
                .id(bodyPart.getId())
                .name(bodyPart.getName())
                .exercises(convertExercises(bodyPart.getExercises()))
                .build();
    }
}
