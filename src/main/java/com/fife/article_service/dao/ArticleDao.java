package com.fife.article_service.dao;

import com.fife.article_service.model.ArticleModel;

import java.util.List;
import java.util.Optional;

public interface ArticleDao {
    ArticleModel save(ArticleModel article);
    List<ArticleModel> findAll();
    Optional<ArticleModel> findById(Long id);
    void delete(ArticleModel article);
}
