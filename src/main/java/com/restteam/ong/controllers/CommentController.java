package com.restteam.ong.controllers;

import com.restteam.ong.controllers.dto.CommentBodyResponse;
import com.restteam.ong.controllers.dto.CommentDTO;
import com.restteam.ong.models.Comment;
import com.restteam.ong.models.impl.UserDetailsImpl;
import com.restteam.ong.services.CommentService;
import com.restteam.ong.services.NewsService;
import com.restteam.ong.services.RoleService;
import com.restteam.ong.services.UserService;
import com.restteam.ong.util.BindingResultsErrors;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private final CommentService commentService;

    @Autowired
    private final NewsService newsService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    private final ModelMapper modelMapper = new ModelMapper();

    @DeleteMapping(path = "/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId, @Parameter(hidden = true)@AuthenticationPrincipal UserDetailsImpl userDetails){
        try{
            commentService.deleteComment(commentId,userDetails);
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
                .map( comment -> modelMapper.map(comment, CommentBodyResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(commentsDto);
    }

    @PostMapping()
    public ResponseEntity<?> createComment(@RequestBody @Valid CommentDTO commentDTO,
                                           @Parameter(hidden = true) BindingResult bindingResult,
                                           @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails){
        //Iniciamos la variable para hacer la respuesta.
        ResponseEntity response;
        //Verificamos que los datos del @Valid estén completos, si hay errores se manda un ResponseEntity con ellos.
        if(bindingResult.hasErrors()){
            response = BindingResultsErrors.getResponseEntityWithErrors(bindingResult);
        }
        //De lo contrario se procese a guardar la entidad.
        else{
            //Hacemos try-catch debido a que podría ya existir la entidad y de ese modo podemos controlar la excepcion por parte del service.
            try{
                //Creamos el comment mapeando del DTO
                var comment = modelMapper.map(commentDTO,Comment.class);
                //Asignamos el usuario que creó el Comentario (que es el que está registrado en el momento).
                comment.setUser(userDetails.getUser());
                //Mapeamos el newsDTO que viene en el commentDTO a un News.
                comment.setNews(newsService.findByNameOrElseCreateNewNews(commentDTO.getNewsDTO()));
                //Le asignamos la fecha de creacion
                comment.setCreatedAt(System.currentTimeMillis() / 1000);
                //Le asignamos la fecha de actualizacion que por defecto seria la de creación.
                comment.setUpdatedAt(comment.getCreatedAt());
                //Enviamos la respuesta.
                response = ResponseEntity.ok(commentService.createComment(comment));
            } catch (Exception e){
                response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable("id") Long id,@RequestBody CommentBodyResponse commentDTO,
                                           @Parameter(hidden = true)
                                           @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        ResponseEntity response ;
        if(! commentService.exitsCommentsById(id)){
           return response = ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("no existe el comentario");
        }
        if(! userService.userCanModifyUserWithId(userDetailsImpl,commentService.getById(id).getUser().getId())){
            return  response = ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("usted no puede modificar este comentario");
        }
        else{
            try{
                 response = ResponseEntity.ok(commentService.updateComment(id,commentDTO));
            }
            catch (Exception e){
                response = ResponseEntity.status(HttpStatus.BAD_REQUEST).
                        body("no se pudo modificar el comentario");
            }
            return response;
        }

    }


}
