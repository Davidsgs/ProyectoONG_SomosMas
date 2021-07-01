package com.restteam.ong.controllers.dto;

import com.restteam.ong.models.Role;
import lombok.Data;

@Data
public class UserDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String photo;

    private Role role;
}
