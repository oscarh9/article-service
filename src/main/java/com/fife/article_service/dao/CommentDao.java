package com.fife.article_service.dao;

import com.fife.article_service.model.Comment;

public interface CommentDao {
    Comment save(Comment comment);
}
