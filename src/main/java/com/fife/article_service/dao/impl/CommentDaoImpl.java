package com.fife.article_service.dao.impl;

import com.fife.article_service.dao.CommentDao;
import com.fife.article_service.entity.CommentEntity;
import com.fife.article_service.model.Comment;
import com.fife.article_service.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommentDaoImpl implements CommentDao {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    @Override
    public Comment save(Comment comment) {
        CommentEntity entity = modelMapper.map(comment, CommentEntity.class);
        CommentEntity saved = commentRepository.save(entity);
        return modelMapper.map(saved, Comment.class);
    }

    @Override
    public List<Comment> findByArticleId(String articleId) {
        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(entity -> modelMapper.map(entity, Comment.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Comment> findById(String commentId) {
        return commentRepository.findById(commentId)
                .map(entity -> modelMapper.map(entity, Comment.class));
    }

    @Override
    public Optional<Comment> findByIdAndArticleId(String commentId, String articleId) {
        return commentRepository.findByIdAndArticleId(commentId, articleId)
                .map(entity -> modelMapper.map(entity, Comment.class));
    }


    @Override
    public Comment updateComment(Comment comment) {
        CommentEntity entity = modelMapper.map(comment, CommentEntity.class);
        CommentEntity saved = commentRepository.save(entity);
        return modelMapper.map(saved, Comment.class);
    }

    @Override
    public void delete(Comment comment) {
        CommentEntity entity = modelMapper.map(comment, CommentEntity.class);
        commentRepository.delete(entity);
    }

    @Override
    public void deleteByArticleId(String articleId) {
        commentRepository.deleteByArticleId(articleId);
    }
}
