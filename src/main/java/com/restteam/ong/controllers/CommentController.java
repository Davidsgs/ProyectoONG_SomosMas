package com.restteam.ong.controllers;


import com.restteam.ong.controllers.dto.BodyOfCommentDTO;
import com.restteam.ong.services.CommentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final ModelMapper modelMapper = new ModelMapper();

    @DeleteMapping(path = "/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId){
        try{
            commentService.deleteComment(commentId);
            return new ResponseEntity<>("Comment deleted successfully.", HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity<>("Couldn't delete comment",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllComments(){
        var comments = commentService.getAllComments();
        var commentsDto = comments.stream()
                .map( comment -> modelMapper.map(comment, BodyOfCommentDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(commentsDto);
    }
}
