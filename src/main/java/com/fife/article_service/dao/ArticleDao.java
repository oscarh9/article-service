package com.fife.article_service.dao;

import com.fife.article_service.model.Article;
import java.util.List;
import java.util.Optional;

public interface ArticleDao {
    Article save(Article article);

    List<Article> findAll();

    Optional<Article> findById(Long id);

    void delete(Article article);
}
