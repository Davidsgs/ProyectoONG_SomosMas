package com.restteam.ong.services.impl;


import com.restteam.ong.controllers.dto.CommentBodyResponse;
import com.restteam.ong.models.Comment;
import com.restteam.ong.models.User;
import com.restteam.ong.models.impl.UserDetailsImpl;
import com.restteam.ong.repositories.CommentRepository;
import com.restteam.ong.services.CommentService;
import com.restteam.ong.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserService userService;

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
    public void deleteComment(Long commentId, UserDetailsImpl userDetails){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new IllegalStateException(String.format(COMMENT_NOT_FOUND,commentId)));
        if(userService.userCanModifyUserWithId(userDetails,commentUserId(comment))){
            commentRepository.deleteById(commentId);
        } else{
            throw new BadCredentialsException("Current user isn't comment owner nor admin of this site.");
        }
    }

    //aux
    private boolean userIsAdmin(User user){ return(user.getRole().getName().equals("ROLE_ADMIN")); }
    private Long commentUserId(Comment comment){ return comment.getUser().getId(); }

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

    @Override
    public Boolean exitsCommentsById(Long id){
        return commentRepository.existsById(id);
    }

    @Override
    @Transactional
    public Comment updateComment(Long id, CommentBodyResponse commentDTO){
        Comment comment = this.getById(id);
        comment.setBody(commentDTO.getBody());
        comment.setCreatedAt(new Date().getTime() / 1000);
        comment.setUpdatedAt(comment.getCreatedAt());
        return commentRepository.findById(id).orElseThrow(
                () -> new IllegalStateException(String.format(COMMENT_NOT_FOUND,id))
        );
    }
}
