package com.restteam.ong.services;

import com.restteam.ong.models.Comment;

import java.util.List;

public interface CommentService {

    public Comment createComment(Comment comment);

    void deleteComment(Long commentId);

    public List<Comment> getAllComments();

    public Comment getById(Long id);

}
