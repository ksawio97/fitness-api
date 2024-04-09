package com.ksawio.fitnessapi.dto;

import com.ksawio.fitnessapi.entities.Article;
import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDto {
    private Long id;
    private String title;
    private String author;
    private String publishDate;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public ArticleDto(Long id, String title, String author, Date publishDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publishDate = dateFormat.format(publishDate);
    }

    public static ArticleDto createFromArticle(@NonNull Article article) {
        String formattedDate = dateFormat.format(article.getPublishDate());

        return ArticleDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .author(article.getAuthor())
                .publishDate(formattedDate)
                .build();
    }
}
