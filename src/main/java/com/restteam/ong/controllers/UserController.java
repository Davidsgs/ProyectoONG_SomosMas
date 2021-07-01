package com.restteam.ong.controllers;

import com.restteam.ong.controllers.dto.UserDTO;
import com.restteam.ong.models.impl.UserDetailsImpl;
import com.restteam.ong.services.RoleService;
import com.restteam.ong.services.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    private final String USER_WITH_ID_NOT_FOUND = "User with id: %d not found";
    
    @GetMapping
    ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PatchMapping("/{id}")
    ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO,
                                 @PathVariable("id") Long id,
                                 @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ResponseEntity response = ResponseEntity.status(HttpStatus.FORBIDDEN).body("You don't have permissions to do that.");
        if(userCanModifyUserWithId(userDetails,id)) {
            try {
                response = ResponseEntity.ok(userService.updateUser(id, userDTO));
            } catch (IllegalStateException e) {
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } catch (Exception e) {
                response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
        return response;
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<?> deleteUser(@PathVariable("id") Long id,
                                 @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ResponseEntity response = ResponseEntity.status(HttpStatus.FORBIDDEN).body("You don't have permissions to do that.");
        if (userCanModifyUserWithId(userDetails,id)) {
            response = ResponseEntity.ok(userService.deleteUser(id));
            if (response.getBody().toString() == "false") {
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format(USER_WITH_ID_NOT_FOUND, id));
            }
        }
        return response;
    }

    public boolean userCanModifyUserWithId(UserDetailsImpl userDetailsImpl, Long id) {
        var adminRole = roleService.findByName("ROLE_ADMIN");
        var bool = userDetailsImpl.getUser().getRole().getName().contentEquals(adminRole.getName());
        if (!bool) {
            bool = userDetailsImpl.getUser().getId() == id;
        }
        return bool;
    }
}
