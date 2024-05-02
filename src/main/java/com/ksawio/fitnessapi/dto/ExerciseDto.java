package com.ksawio.fitnessapi.dto;

import com.ksawio.fitnessapi.entities.BodyPart;
import com.ksawio.fitnessapi.entities.Exercise;
import com.ksawio.fitnessapi.utils.Difficulty;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.util.Pair;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseDto {
    private Long id;
    private String title;
    private Difficulty difficulty;
    @Nullable
    private Pair<String, String> mediaLinks;
    private List<String> instructions;
    private Set<Long> bodyParts;

    public static ExerciseDto createFromExercise(@NonNull Exercise exercise) {
        final var builder = ExerciseDto.builder();

        final var exerciseMediaLinks = exercise.getMediaLinks();
        if (exerciseMediaLinks != null)
            builder.mediaLinks(Pair.of(exerciseMediaLinks.getPrimaryMediaLink(), exerciseMediaLinks.getSecondaryMediaLink()));
        
        return builder
                .id(exercise.getId())
                .title(exercise.getTitle())
                .difficulty(exercise.getDifficulty())
                .instructions(exercise.getInstructions())
                .bodyParts(exercise.getBodyParts().stream().map(BodyPart::getId).collect(Collectors.toSet()))
                .build();
    }
}

