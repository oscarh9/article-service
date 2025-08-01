package com.fife.article_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fife.article_service.dto.ArticleRequestDTO;
import com.fife.article_service.dto.ArticleResponseDTO;
import com.fife.article_service.exception.ResourceNotFoundException;
import com.fife.article_service.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArticleController.class)
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ArticleService articleService;
    @Autowired
    private ObjectMapper objectMapper;

    private ArticleResponseDTO sampleResponse;

    @BeforeEach
    void setUp() {
        sampleResponse = new ArticleResponseDTO();
        sampleResponse.setId(1L);
        sampleResponse.setTitle("Sample Title");
        sampleResponse.setContent("Sample Content");
        sampleResponse.setAuthor("Sample Author");
        sampleResponse.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void createArticle_ReturnsArticleResponse() throws Exception {
        ArticleRequestDTO request = new ArticleRequestDTO();
        request.setTitle("Sample Title");
        request.setContent("Sample Content");
        request.setAuthor("Sample Author");

        Mockito.when(articleService.createArticle(any(ArticleRequestDTO.class))).thenReturn(sampleResponse);

        mockMvc.perform(post("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Sample Title"))
                .andExpect(jsonPath("$.content").value("Sample Content"))
                .andExpect(jsonPath("$.author").value("Sample Author"));
    }

    @Test
    void getAllArticles_ReturnsListOfArticles() throws Exception {
        ArticleResponseDTO response2 = new ArticleResponseDTO();
        response2.setId(2L);
        response2.setTitle("Another Title");
        response2.setContent("Another Content");
        response2.setAuthor("Another Author");
        response2.setCreatedAt(LocalDateTime.now());

        Mockito.when(articleService.getAllArticles()).thenReturn(List.of(sampleResponse, response2));

        mockMvc.perform(get("/api/articles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Sample Title"))
                .andExpect(jsonPath("$[1].title").value("Another Title"));
    }
}
