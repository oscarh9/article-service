package com.fife.article_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fife.article_service.dto.ArticleRequest;
import com.fife.article_service.exception.GlobalExceptionHandler;
import com.fife.article_service.exception.NotFoundException;
import com.fife.article_service.model.Article;
import com.fife.article_service.service.ArticleService;
import com.fife.article_service.utils.ApiConstant;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ArticleController.class)
@Import(GlobalExceptionHandler.class)
@AutoConfigureMockMvc(addFilters = false)
public class ArticleControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private ArticleService articleService;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private ModelMapper modelMapper;

    private Article sampleResponse;

    @BeforeEach
    void setUp() {
        sampleResponse = new Article();
        sampleResponse.setId(1L);
        sampleResponse.setTitle("Sample Title");
        sampleResponse.setContent("Sample Content");
        sampleResponse.setAuthor("Sample Author");
        sampleResponse.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void createArticle_ReturnsArticleResponse() throws Exception {
        ArticleRequest request = new ArticleRequest();
        request.setTitle("Sample Title");
        request.setContent("Sample Content");
        request.setAuthor("Sample Author");

        Mockito.when(modelMapper.map(request, Article.class)).thenReturn(sampleResponse);
        Mockito.when(articleService.createArticle(any(Article.class))).thenReturn(sampleResponse);

        mockMvc.perform(
                        post(ApiConstant.API_V1_ARTICLE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Sample Title"))
                .andExpect(jsonPath("$.content").value("Sample Content"))
                .andExpect(jsonPath("$.author").value("Sample Author"));
    }

    @Test
    void getAllArticles_ReturnsListOfArticles() throws Exception {
        Article response2 = new Article();
        response2.setId(2L);
        response2.setTitle("Another Title");
        response2.setContent("Another Content");
        response2.setAuthor("Another Author");
        response2.setCreatedAt(LocalDateTime.now());

        Mockito.when(articleService.getAllArticles())
                .thenReturn(List.of(sampleResponse, response2));

        mockMvc.perform(get(ApiConstant.API_V1_ARTICLE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Sample Title"))
                .andExpect(jsonPath("$[1].title").value("Another Title"));
    }

    @Test
    void getArticleById_WhenFound_ReturnsArticle() throws Exception {
        Mockito.when(articleService.getArticleById(1L)).thenReturn(sampleResponse);

        mockMvc.perform(get(ApiConstant.API_V1_ARTICLE + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Sample Title"))
                .andExpect(jsonPath("$.content").value("Sample Content"));
    }

    @Test
    void getArticleById_WhenNotFound_Returns404() throws Exception {
        Mockito.when(articleService.getArticleById(99L))
                .thenThrow(new NotFoundException("Article not found"));

        mockMvc.perform(get(ApiConstant.API_V1_ARTICLE + "/99")).andExpect(status().isNotFound());
    }

    @Test
    void updateArticle_WhenFound_ReturnsUpdatedArticle() throws Exception {
        ArticleRequest request = new ArticleRequest();
        request.setTitle("Updated Title");
        request.setContent("Updated Content");
        request.setAuthor("Updated Author");

        Article updatedResponse = new Article();
        updatedResponse.setId(1L);
        updatedResponse.setTitle("Updated Title");
        updatedResponse.setContent("Updated Content");
        updatedResponse.setAuthor("Updated Author");
        updatedResponse.setCreatedAt(LocalDateTime.now());

        Mockito.when(modelMapper.map(request, Article.class)).thenReturn(updatedResponse);
        Mockito.when(articleService.updateArticle(eq(1L), any(Article.class)))
                .thenReturn(updatedResponse);

        mockMvc.perform(
                        put(ApiConstant.API_V1_ARTICLE + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.content").value("Updated Content"))
                .andExpect(jsonPath("$.author").value("Updated Author"));
    }

    @Test
    void updateArticle_WhenNotFound_Returns404() throws Exception {
        ArticleRequest request = new ArticleRequest();
        request.setTitle("Updated Title");
        request.setContent("Updated Content");
        request.setAuthor("Someone");

        Article mappedArticle = new Article();
        mappedArticle.setTitle("Updated Title");
        mappedArticle.setContent("Updated Content");
        mappedArticle.setAuthor("Someone");

        Mockito.when(modelMapper.map(request, Article.class)).thenReturn(mappedArticle);

        Mockito.when(articleService.updateArticle(eq(99L), any(Article.class)))
                .thenThrow(new NotFoundException("Article not found"));

        mockMvc.perform(
                        put(ApiConstant.API_V1_ARTICLE + "/99")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteArticle_WhenFound_ReturnsNoContent() throws Exception {
        Mockito.doNothing().when(articleService).deleteArticle(1L);

        mockMvc.perform(delete(ApiConstant.API_V1_ARTICLE + "/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteArticle_WhenNotFound_Returns404() throws Exception {
        Mockito.doThrow(new NotFoundException("Article not found"))
                .when(articleService)
                .deleteArticle(99L);

        mockMvc.perform(delete(ApiConstant.API_V1_ARTICLE + "/99"))
                .andExpect(status().isNotFound());
    }
}
