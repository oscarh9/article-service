package com.fife.article_service.dao;

import com.fife.article_service.model.ArticleModel;

import java.util.List;

public interface ArticleDao {
    ArticleModel save(ArticleModel article);
    List<ArticleModel> findAll();
}
