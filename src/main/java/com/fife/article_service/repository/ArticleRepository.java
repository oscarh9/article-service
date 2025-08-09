package com.fife.article_service.repository;

import com.fife.article_service.entity.ArticleEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleRepository extends MongoRepository<ArticleEntity, String> {}
