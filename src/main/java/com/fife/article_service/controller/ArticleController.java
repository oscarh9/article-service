package com.fife.article_service.controller;

import com.fife.article_service.dto.ArticleRequestDTO;
import com.fife.article_service.dto.ArticleResponseDTO;
import com.fife.article_service.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<ArticleResponseDTO> createArticle(@Valid @RequestBody ArticleRequestDTO articleRequestDTO) {
        ArticleResponseDTO created = articleService.createArticle(articleRequestDTO);
        return ResponseEntity.ok(created);
    }


    @GetMapping
    public ResponseEntity<List<ArticleResponseDTO>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAllArticles());
    }
}
