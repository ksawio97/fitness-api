package com.ksawio.fitnessapi.entities;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"exercise"})
public class ExerciseMediaLinks {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String primaryMediaLink;
    private String secondaryMediaLink;

    @OneToOne(mappedBy = "mediaLinks")
    private Exercise exercise;
}