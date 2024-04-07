package com.ksawio.fitnessapi.dto;

import com.ksawio.fitnessapi.entities.BodyPart;
import com.ksawio.fitnessapi.entities.Exercise;
import com.ksawio.fitnessapi.utils.Difficulty;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseDto {
    private Long id;
    private String title;
    private Difficulty difficulty;
    private List<String> instructions;
    private Set<Long> bodyParts;

    public static ExerciseDto createFromExercise(@NonNull Exercise exercise) {
        return ExerciseDto.builder()
                .id(exercise.getId())
                .title(exercise.getTitle())
                .difficulty(exercise.getDifficulty())
                .instructions(exercise.getInstructions())
                .bodyParts(exercise.getBodyParts().stream().map(BodyPart::getId).collect(Collectors.toSet()))
                .build();
    }
}

