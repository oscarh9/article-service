package com.fife.article_service.repository;

import com.fife.article_service.entity.CommentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<CommentEntity, String> {
    List<CommentEntity> findByArticleId(String articleId);
}
