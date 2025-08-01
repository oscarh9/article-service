package com.fife.article_service.service.impl;

import com.fife.article_service.dao.ArticleDao;
import com.fife.article_service.dto.ArticleRequestDTO;
import com.fife.article_service.dto.ArticleResponseDTO;
import com.fife.article_service.exception.ResourceNotFoundException;
import com.fife.article_service.model.ArticleModel;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ArticleServiceImplTest {

    private ArticleServiceImpl articleService;
    private ArticleDao articleDao;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        articleDao = mock(ArticleDao.class);
        modelMapper = new ModelMapper();
        articleService = new ArticleServiceImpl(articleDao, modelMapper);
    }

    @Test
    void createArticle_WithValidRequest_ReturnsResponseDTO() {
        ArticleRequestDTO request = new ArticleRequestDTO();
        request.setTitle("Test Title");
        request.setContent("Test Content");
        ArticleModel savedModel = new ArticleModel(
                1L,
                "Test Title",
                "Test Content",
                "Test Author",
                LocalDateTime.now()
        );
        when(articleDao.save(any(ArticleModel.class))).thenReturn(savedModel);

        ArticleResponseDTO response = articleService.createArticle(request);

        assertEquals("Test Title", response.getTitle());
        assertEquals("Test Content", response.getContent());
        verify(articleDao).save(any(ArticleModel.class));
    }

    @Test
    void getAllArticles_WhenCalled_ReturnsListOfResponseDTOs() {
        List<ArticleModel> models = List.of(
                new ArticleModel(1L, "Title 1", "Content 1", "Author 1", LocalDateTime.now()),
                new ArticleModel(2L, "Title 2", "Content 2", "Author 2", LocalDateTime.now())
        );

        when(articleDao.findAll()).thenReturn(models);

        List<ArticleResponseDTO> responses = articleService.getAllArticles();

        assertEquals(2, responses.size());
        assertEquals("Title 1", responses.get(0).getTitle());
        assertEquals("Title 2", responses.get(1).getTitle());
        verify(articleDao).findAll();
    }

    @Test
    void getArticleById_WhenArticleExists_ReturnsArticleModel() {
        Long articleId = 1L;
        ArticleModel model = new ArticleModel(
                articleId,
                "Title Example",
                "Content Example",
                "Author",
                LocalDateTime.now()
        );

        when(articleDao.findById(articleId)).thenReturn(Optional.of(model));

        ArticleResponseDTO result = articleService.getArticleById(articleId);

        assertEquals(model.getTitle(), result.getTitle());
        assertEquals(model.getContent(), result.getContent());
        verify(articleDao).findById(articleId);
    }

    @Test
    void getArticleById_WhenArticleDoesNotExist_ThrowsResourceNotFoundException() {
        Long articleId = 99L;
        when(articleDao.findById(articleId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> articleService.getArticleById(articleId));
        verify(articleDao).findById(articleId);
    }

}
