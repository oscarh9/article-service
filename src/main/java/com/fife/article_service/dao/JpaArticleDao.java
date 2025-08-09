package com.fife.article_service.dao;

import com.fife.article_service.entity.ArticleEntity;
import com.fife.article_service.model.Article;
import com.fife.article_service.repository.ArticleRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaArticleDao implements ArticleDao {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    @Override
    public Article save(Article article) {
        ArticleEntity entity = modelMapper.map(article, ArticleEntity.class);
        ArticleEntity saved = articleRepository.save(entity);
        return modelMapper.map(saved, Article.class);
    }

    @Override
    public List<Article> findAll() {
        return articleRepository.findAll().stream()
                .map(entity -> modelMapper.map(entity, Article.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Article> findById(String id) {
        return articleRepository.findById(id).map(entity -> modelMapper.map(entity, Article.class));
    }

    @Override
    public void delete(Article article) {
        ArticleEntity entity = modelMapper.map(article, ArticleEntity.class);
        articleRepository.delete(entity);
    }
}
