package com.fife.article_service.service;

import com.fife.article_service.model.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(String articleId, Comment comment);
    List<Comment> getCommentsByArticleId (String articleId);
    Comment getCommentById (String commentId);
    Comment updateComment (String articleId, String commentId, Comment comment);
    void deleteComment(String articleId, String commentId);
}
