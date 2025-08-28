package com.fife.article_service.service;

import com.fife.article_service.model.Comment;

public interface CommentService {
    Comment createComment(String articleId, Comment comment);
}
