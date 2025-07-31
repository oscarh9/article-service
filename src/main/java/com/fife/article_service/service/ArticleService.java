package com.fife.article_service.service;

import com.fife.article_service.dto.ArticleRequestDTO;
import com.fife.article_service.dto.ArticleResponseDTO;

import java.util.List;

public interface ArticleService {
    ArticleResponseDTO createArticle(ArticleRequestDTO articleRequestDTO);
    List<ArticleResponseDTO> getAllArticles();
}
