package com.fife.article_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentRequest {

    @NotBlank(message = "Content cannot be empty")
    private String content;

    @NotBlank(message = "Author cannot be empty")
    private String author;
}
