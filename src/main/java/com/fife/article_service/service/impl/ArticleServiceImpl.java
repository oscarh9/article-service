package com.fife.article_service.service.impl;

import com.fife.article_service.dao.ArticleDao;
import com.fife.article_service.dto.ArticleRequestDTO;
import com.fife.article_service.dto.ArticleResponseDTO;
import com.fife.article_service.model.ArticleModel;
import com.fife.article_service.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleDao articleDao;
    private final ModelMapper modelMapper;

    @Override
    public ArticleResponseDTO createArticle(ArticleRequestDTO articleRequestDTO) {
        ArticleModel articleModel = modelMapper.map(articleRequestDTO, ArticleModel.class);
        articleModel.setCreatedAt(LocalDateTime.now());
        ArticleModel saved = articleDao.save(articleModel);
        return modelMapper.map(saved, ArticleResponseDTO.class);
    }

    @Override
    public List<ArticleResponseDTO> getAllArticles() {
        return articleDao.findAll().stream()
                .map(articleModel -> modelMapper.map(articleModel, ArticleResponseDTO.class))
                .collect(Collectors.toList());
    }
}
