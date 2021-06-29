package com.restteam.ong.controllers;
import com.restteam.ong.models.User;
import com.restteam.ong.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.geom.NoninvertibleTransformException;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    private final String USER_WITH_ID_NOT_FOUND = "User with id: %d not found";

    private final String UNKNOWN_ERROR = "Unknown error";

    @GetMapping
    ResponseEntity<?> getUsers(){
        return ResponseEntity.ok(userService.findAll());
    }

    @PatchMapping("/{id}")
    ResponseEntity<?> updateUser(@RequestBody User user ,@PathVariable("id") Long id){
        ResponseEntity response;
        try{
            response = ResponseEntity.ok(userService.updateUser(id,user));
        }catch (IllegalStateException e){
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UNKNOWN_ERROR);
        }
        return response;
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        ResponseEntity response = ResponseEntity.ok(userService.deleteUser(id));
        if (response.getBody().toString() == "false"){
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format(USER_WITH_ID_NOT_FOUND,id));
        }
        return response;
    }
}
