package com.fife.article_service.service.impl;

import com.fife.article_service.dao.ArticleDao;
import com.fife.article_service.dto.ArticleRequestDTO;
import com.fife.article_service.dto.ArticleResponseDTO;
import com.fife.article_service.model.ArticleModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    @DisplayName("createArticle() should return a valid response DTO")
    void testCreateArticle() {

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
    @DisplayName("getAllArticles() should return a list of response DTOs")
    void testGetAllArticles() {
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
}
