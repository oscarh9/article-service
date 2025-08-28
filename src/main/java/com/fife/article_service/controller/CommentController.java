package com.fife.article_service.controller;

import com.fife.article_service.dto.CommentRequest;
import com.fife.article_service.model.Comment;
import com.fife.article_service.service.CommentService;
import com.fife.article_service.utils.ApiConstant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstant.API_V1_ARTICLE + ApiConstant.ID + ApiConstant.COMMENT)
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<Comment> createComment(
            @PathVariable String id, @Valid @RequestBody CommentRequest commentRequest) {
        Comment comment = modelMapper.map(commentRequest, Comment.class);
        return ResponseEntity.ok(commentService.createComment(id, comment));
    }
}
