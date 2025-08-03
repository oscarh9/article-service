package com.fife.article_service.dao;

import com.fife.article_service.entity.ArticleEntity;
import com.fife.article_service.model.ArticleModel;
import com.fife.article_service.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    @Test
    void findAll_shouldReturnMappedList() {
        ArticleEntity entity1 = new ArticleEntity();
        entity1.setId(1L);
        ArticleEntity entity2 = new ArticleEntity();
        entity2.setId(2L);

        ArticleModel model1 = new ArticleModel(1L, "T1", "C1", "A1", LocalDateTime.now());
        ArticleModel model2 = new ArticleModel(2L, "T2", "C2", "A2", LocalDateTime.now());

        when(articleRepository.findAll()).thenReturn(List.of(entity1, entity2));
        when(modelMapper.map(entity1, ArticleModel.class)).thenReturn(model1);
        when(modelMapper.map(entity2, ArticleModel.class)).thenReturn(model2);

        List<ArticleModel> result = jpaArticleDao.findAll();

        assertEquals(2, result.size());
        assertEquals("T1", result.get(0).getTitle());
        assertEquals("T2", result.get(1).getTitle());
    }

    @Test
    void findById_shouldReturnMappedModelIfFound() {
        Long id = 1L;
        ArticleEntity entity = new ArticleEntity();
        ArticleModel model = new ArticleModel(id,"Title", "Content", "Author", LocalDateTime.now());

        when(articleRepository.findById(id)).thenReturn(Optional.of(entity));
        when(modelMapper.map(entity, ArticleModel.class)).thenReturn(model);

        Optional<ArticleModel> result = jpaArticleDao.findById(id);

        assertTrue(result.isPresent());
        assertEquals("Title", result.get().getTitle());;
    }

    @Test
    void findById_shouldReturnEmptyIfNotFound() {
        when(articleRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<ArticleModel> result = jpaArticleDao.findById(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void delete_shouldMapAndDeleteEntity() {
        ArticleModel model = new ArticleModel(1L, "Title", "Content", "Author", LocalDateTime.now());
        ArticleEntity entity = new ArticleEntity();

        when(modelMapper.map(model, ArticleEntity.class)).thenReturn(entity);

        jpaArticleDao.delete(model);

        verify(articleRepository).delete(entity);
    }
}
