package com.fife.article_service.dao;

import com.fife.article_service.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao {
    Comment save(Comment comment);
    List<Comment> findByArticleId(String articleId);
    Optional<Comment> findById(String commentId);
    Optional<Comment> findByIdAndArticleId(String commentId, String articleId);
    Comment updateComment(Comment comment);
    void delete(Comment comment);
    void deleteByArticleId(String articleId);
}
