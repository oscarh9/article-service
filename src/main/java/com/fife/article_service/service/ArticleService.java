package com.fife.article_service.service;

import com.fife.article_service.model.Article;
import java.util.List;

public interface ArticleService {
    Article createArticle(Article article);

    List<Article> getAllArticles();

    Article getArticleById(String id);

    Article updateArticle(String id, Article article);

    void deleteArticle(String id);
}
