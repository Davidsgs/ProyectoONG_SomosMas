package com.restteam.ong.controllers;

import com.restteam.ong.controllers.dto.BodyOfCommentDTO;
import com.restteam.ong.controllers.dto.CommentDTO;
import com.restteam.ong.models.Comment;
import com.restteam.ong.models.impl.UserDetailsImpl;
import com.restteam.ong.services.CommentService;
import com.restteam.ong.util.BindingResultsErrors;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final ModelMapper modelMapper = new ModelMapper();

    @DeleteMapping(path = "/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId,
                                                @Parameter(hidden = true)@AuthenticationPrincipal UserDetailsImpl userDetails){
        try{
            commentService.deleteComment(commentId,userDetails.getUser());
            return new ResponseEntity<>("Comment deleted successfully.", HttpStatus.OK);
        }
        catch(IllegalStateException ise){
            return new ResponseEntity<>("Couldn't find comment.",HttpStatus.NOT_FOUND);
        }
        catch (BadCredentialsException bce){
            return new ResponseEntity<>("Current user isn't comment owner nor admin of this site",HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAllComments(){
        var comments = commentService.getAllComments();
        var commentsDto = comments.stream()
                .map( comment -> modelMapper.map(comment, BodyOfCommentDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(commentsDto);
    }

    @PostMapping()
    public ResponseEntity<?> createComment(@RequestBody @Valid CommentDTO commentDTO,
                                           @Parameter(hidden = true) BindingResult bindingResult){
        ResponseEntity response;
        if(bindingResult.hasErrors()){
            response = BindingResultsErrors.getResponseEntityWithErrors(bindingResult);
        }
        else{
            try{
                var comment = modelMapper.map(commentDTO,Comment.class);
                comment.setCreatedAt(System.currentTimeMillis() / 1000);
                comment.setUpdatedAt(comment.getCreatedAt());
                response = ResponseEntity.ok(commentService.createComment(comment));
            } catch (Exception e){
                response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
        return response;
    }
}
