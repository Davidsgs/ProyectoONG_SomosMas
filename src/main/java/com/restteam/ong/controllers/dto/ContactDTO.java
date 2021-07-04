package com.restteam.ong.controllers.dto;

import lombok.Data;

@Data
public class ContactDTO {
    private String name;
    private String phone;
    private String email;
    private String message;
}
