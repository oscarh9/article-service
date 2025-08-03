package com.fife.article_service.service.impl;

import com.fife.article_service.dao.ArticleDao;
import com.fife.article_service.dto.ArticleRequestDTO;
import com.fife.article_service.dto.ArticleResponseDTO;
import com.fife.article_service.exception.ResourceNotFoundException;
import com.fife.article_service.model.ArticleModel;
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

    @Test
    void updateArticle_WhenArticleExists_ReturnsUpdatedResponseDTO() {
        Long articleId = 1L;
        ArticleRequestDTO request = new ArticleRequestDTO();
        request.setTitle("Updated Title");
        request.setContent("Updated Content");
        request.setAuthor("Updated Author");

        ArticleModel existingModel = new ArticleModel(
                articleId,
                "Old Title",
                "Old Content",
                "Old Author",
                LocalDateTime.now()
        );

        ArticleModel updatedModel = new ArticleModel(
                articleId,
                "Updated Title",
                "Updated Content",
                "Updated Author",
                LocalDateTime.now()
        );

        when(articleDao.findById(articleId)).thenReturn(Optional.of(existingModel));
        when(articleDao.save(any(ArticleModel.class))).thenReturn(updatedModel);

        ArticleResponseDTO response = articleService.updateArticle(articleId, request);

        assertEquals("Updated Title", response.getTitle());
        assertEquals("Updated Content", response.getContent());
        assertEquals("Updated Author", response.getAuthor());
        verify(articleDao).findById(articleId);
        verify(articleDao).save(any(ArticleModel.class));
    }


    @Test
    void updateArticle_WhenArticleDoesNotExist_ThrowsResourceNotFoundException() {
        Long articleId = 99L;
        ArticleRequestDTO request = new ArticleRequestDTO();
        request.setTitle("Updated Title");
        request.setContent("Updated Content");

        when(articleDao.findById(articleId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> articleService.updateArticle(articleId, request));
        verify(articleDao).findById(articleId);
    }

    @Test
    void deleteArticle_WhenArticleExists_DeletesArticle() {
        Long articleId = 1L;

        ArticleModel existingModel = new ArticleModel(
                articleId,
                "Title",
                "Content",
                "Author",
                LocalDateTime.now()
        );

        when(articleDao.findById(articleId)).thenReturn(Optional.of(existingModel));
        doNothing().when(articleDao).delete(existingModel);

        assertDoesNotThrow(() -> articleService.deleteArticle(articleId));

        verify(articleDao).findById(articleId);
        verify(articleDao).delete(existingModel);
    }

    @Test
    void deleteArticle_WhenArticleDoesNotExist_ThrowsResourceNotFoundException() {
        Long articleId = 99L;
        when(articleDao.findById(articleId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> articleService.deleteArticle(articleId));

        verify(articleDao).findById(articleId);
        verify(articleDao, never()).delete(any());
    }


}
