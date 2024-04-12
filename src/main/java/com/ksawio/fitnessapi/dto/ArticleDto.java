package com.ksawio.fitnessapi.dto;

import com.ksawio.fitnessapi.entities.Article;
import lombok.*;

import java.text.SimpleDateFormat;

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

    public ArticleDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.author = article.getAuthor();
        this.publishDate = dateFormat.format(article.getPublishDate());
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
