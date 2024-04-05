package com.ksawio.fitnessapi.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BodyPart {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "bodyParts")
    private Set<Exercise> exercises;
}
