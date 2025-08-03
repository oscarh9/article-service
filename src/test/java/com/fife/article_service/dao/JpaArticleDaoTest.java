package com.fife.article_service.dao;

import com.fife.article_service.entity.ArticleEntity;
import com.fife.article_service.model.ArticleModel;
import com.fife.article_service.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class JpaArticleDaoTest {

    private ArticleRepository articleRepository;
    private ModelMapper modelMapper;
    private JpaArticleDao jpaArticleDao;

    @BeforeEach
    void SetUp() {
        articleRepository = mock(ArticleRepository.class);
        modelMapper = mock(ModelMapper.class);
        jpaArticleDao = new JpaArticleDao(articleRepository, modelMapper);
    }

    @Test
    void save_shouldMapAndSaveArticle() {
        ArticleModel input = new ArticleModel(1L, "Title", "Content", "Author", LocalDateTime.now());
        ArticleEntity entity = new ArticleEntity();
        entity.setCreatedAt(input.getCreatedAt());
        ArticleEntity savedEntity = new ArticleEntity();
        ArticleModel output = new ArticleModel(1L, "Title", "Content", "Author", input.getCreatedAt());

        when(modelMapper.map(input, ArticleEntity.class)).thenReturn(entity);
        when(articleRepository.save(entity)).thenReturn(savedEntity);
        when(modelMapper.map(savedEntity, ArticleModel.class)).thenReturn(output);

        ArticleModel result = jpaArticleDao.save(input);

        assertEquals(output.getTitle(), result.getTitle());
        verify(articleRepository).save(entity);
    }
}
