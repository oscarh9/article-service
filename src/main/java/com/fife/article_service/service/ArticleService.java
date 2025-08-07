package com.fife.article_service.service;

import com.fife.article_service.model.Article;
import java.util.List;

public interface ArticleService {
    Article createArticle(Article article);

    List<Article> getAllArticles();

    Article getArticleById(Long id);

    Article updateArticle(Long id, Article article);

    void deleteArticle(Long id);
}
