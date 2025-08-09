package com.fife.article_service.service.impl;

import com.fife.article_service.dao.ArticleDao;
import com.fife.article_service.exception.NotFoundException;
import com.fife.article_service.model.Article;
import com.fife.article_service.service.ArticleService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleDao articleDao;

    @Override
    public Article createArticle(Article article) {
        article.setCreatedAt(LocalDateTime.now());
        return articleDao.save(article);
    }

    @Override
    public List<Article> getAllArticles() {
        return articleDao.findAll();
    }

    @Override
    public Article getArticleById(String id) {
        return articleDao
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Article not found with id " + id));
    }

    @Override
    public Article updateArticle(String id, Article article) {
        Article existing = getArticleById(id);
        existing.setTitle(article.getTitle());
        existing.setContent(article.getContent());
        existing.setUpdatedAt(LocalDateTime.now());
        return articleDao.save(existing);
    }

    @Override
    public void deleteArticle(String id) {
        Article article = getArticleById(id);
        articleDao.delete(article);
    }
}
