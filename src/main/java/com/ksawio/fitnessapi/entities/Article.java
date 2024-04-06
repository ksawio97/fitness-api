package com.ksawio.fitnessapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 3_000)
    private String content;

    @Column(nullable = false)
    private String author;

    @Column(name = "publish_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishDate;
}
