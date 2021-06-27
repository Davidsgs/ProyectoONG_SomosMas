package com.restteam.ong.controllers.dto;

import com.restteam.ong.models.Role;
import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class UserDTO {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;
    @NotBlank
    private String password;

    private Role role;
}
