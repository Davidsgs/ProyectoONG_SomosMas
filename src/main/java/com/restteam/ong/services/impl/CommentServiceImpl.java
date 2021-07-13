package com.restteam.ong.services.impl;


import com.restteam.ong.models.Comment;
import com.restteam.ong.repositories.CommentRepository;
import com.restteam.ong.services.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    private final String COMMENT_NOT_FOUND = "Comment with id: %d. Not found.";

    @Override
    public Comment createComment(Comment comment) {
        if(comment.getId() != null){
            throw new IllegalStateException("The comment can't have id before is created.");
        } else if(comment == null){
            throw new IllegalStateException("The comment can't be null.");
        }
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId){
        boolean exists = commentRepository.existsById(commentId);
        if (!exists){
            throw new IllegalStateException(String.format(COMMENT_NOT_FOUND,commentId));
        }
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAllByOrderByCreatedAt();
    }

    @Override
    public Comment getById(Long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new IllegalStateException(String.format(COMMENT_NOT_FOUND,id))
        );
    }
}
