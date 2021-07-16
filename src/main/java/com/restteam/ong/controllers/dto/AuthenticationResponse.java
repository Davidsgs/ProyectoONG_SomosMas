package com.restteam.ong.controllers.dto;

import lombok.Data;

@Data
public class AuthenticationResponse{

    private String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public AuthenticationResponse() {
    }
}
