package com.fife.article_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;
}
