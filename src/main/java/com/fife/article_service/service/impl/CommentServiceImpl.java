package com.fife.article_service.service.impl;

import com.fife.article_service.dao.CommentDao;
import com.fife.article_service.exception.NotFoundException;
import com.fife.article_service.model.Comment;
import com.fife.article_service.service.CommentService;
import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public List<Comment> getCommentsByArticleId(String articleId) {
        return commentDao.findByArticleId(articleId);
    }

    @Override
    public Comment getCommentById(String commentId) {
        return commentDao.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment not found with id " + commentId));
    }



    @Override
    public Comment updateComment(String articleId, String commentId, Comment comment) {
        Comment existing =  commentDao.findByIdAndArticleId(commentId, articleId)
                        .orElseThrow(() -> new RuntimeException("Comment not found"));
        existing.setContent(comment.getContent());
        existing.setUpdatedAt(LocalDateTime.now());
        return commentDao.updateComment(existing);
    }

    @Override
    public void deleteComment(String articleId, String commentId) {
        Comment existing = commentDao.findByIdAndArticleId(commentId, articleId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        commentDao.delete(existing);
    }
}
