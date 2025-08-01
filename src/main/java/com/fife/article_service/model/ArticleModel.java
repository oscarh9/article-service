package com.fife.article_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleModel {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;
}
