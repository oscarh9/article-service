package com.fife.article_service.dao.impl;

import com.fife.article_service.dao.CommentDao;
import com.fife.article_service.entity.CommentEntity;
import com.fife.article_service.model.Comment;
import com.fife.article_service.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

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
}
