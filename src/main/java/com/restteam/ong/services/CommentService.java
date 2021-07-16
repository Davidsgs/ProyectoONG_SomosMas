package com.restteam.ong.services;

import com.restteam.ong.controllers.dto.CommentBodyDTO;
import com.restteam.ong.controllers.dto.CommentDTO;
import com.restteam.ong.models.Comment;
import com.restteam.ong.models.User;

import java.util.List;

public interface CommentService {

    public Comment createComment(Comment comment);

    void deleteComment(Long commentId, User user);

    public List<Comment> getAllComments();

    public Comment getById(Long id);

    public Boolean exitsCommentsById(Long id);

    public Object updateComment(Long id, CommentBodyDTO commentDTO);

}
