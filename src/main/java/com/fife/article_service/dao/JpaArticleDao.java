package com.fife.article_service.dao;

import com.fife.article_service.entity.ArticleEntity;
import com.fife.article_service.model.ArticleModel;
import com.fife.article_service.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaArticleDao implements ArticleDao {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    @Override
    public ArticleModel save(ArticleModel article) {
        ArticleEntity entity = modelMapper.map(article, ArticleEntity.class);
        entity.setCreatedAt(article.getCreatedAt());
        ArticleEntity saved = articleRepository.save(entity);
        return modelMapper.map(saved, ArticleModel.class);
    }

    @Override
    public List<ArticleModel> findAll() {
        return articleRepository.findAll().stream()
                .map(entity -> modelMapper.map(entity, ArticleModel.class))
                .collect(Collectors.toList());
    }
}
