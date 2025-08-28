package com.fife.article_service.service.impl;

import com.fife.article_service.dao.CommentDao;
import com.fife.article_service.model.Comment;
import com.fife.article_service.service.CommentService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;

    @Override
    public Comment createComment(String articleId, Comment comment) {
        comment.setArticleId(articleId);
        comment.setCreatedAt(LocalDateTime.now());
        return commentDao.save(comment);
    }
}
