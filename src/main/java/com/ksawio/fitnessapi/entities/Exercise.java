package com.ksawio.fitnessapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ksawio.fitnessapi.utils.Difficulty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"bodyParts"})
public class Exercise {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated
    private Difficulty difficulty;

    @ElementCollection
    @OrderColumn(name = "instruction_order")
    @Column(name = "instruction")
    private List<String> instructions;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="body_part_exercise",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "body_part_id"))
    @JsonIgnoreProperties("exercises")
    private Set<BodyPart> bodyParts;
}