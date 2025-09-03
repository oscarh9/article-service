package com.fife.article_service.controller;

import com.fife.article_service.dto.ArticleRequest;
import com.fife.article_service.model.Article;
import com.fife.article_service.service.ArticleService;
import com.fife.article_service.utils.ApiConstant;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstant.API_V1_ARTICLE)
@RequiredArgsConstructor
public class ArticleController {

    private final ModelMapper modelMapper;
    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<Article> createArticle(
            @Valid @RequestBody ArticleRequest articleRequest) {
        Article article = modelMapper.map(articleRequest, Article.class);
        return ResponseEntity.ok(articleService.createArticle(article));
    }

    @GetMapping
    public ResponseEntity<List<Article>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAllArticles());
    }

    @GetMapping(ApiConstant.ARTICLE_ID)
    public ResponseEntity<Article> getArticleById(@PathVariable String articleId) {
        return ResponseEntity.ok(articleService.getArticleById(articleId));
    }

    @PutMapping(ApiConstant.ARTICLE_ID)
    public ResponseEntity<Article> updateArticle(
            @PathVariable String articleId, @Valid @RequestBody ArticleRequest articleRequest) {
        Article article = modelMapper.map(articleRequest, Article.class);
        return ResponseEntity.ok(articleService.updateArticle(articleId, article));
    }

    @DeleteMapping(ApiConstant.ARTICLE_ID)
    public ResponseEntity<Void> deleteArticle(@PathVariable String articleId) {
        articleService.deleteArticle(articleId);
        return ResponseEntity.noContent().build();
    }
}
