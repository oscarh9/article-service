package com.fife.article_service.service;

import com.fife.article_service.dto.ArticleRequestDTO;
import com.fife.article_service.dto.ArticleResponseDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ArticleService {
    ArticleResponseDTO createArticle(ArticleRequestDTO articleRequestDTO);
    List<ArticleResponseDTO> getAllArticles();
    ArticleResponseDTO getArticleById(Long id);
    ArticleResponseDTO updateArticle(Long id, ArticleRequestDTO articleRequestDTO);
    void deleteArticle(Long id);
}
