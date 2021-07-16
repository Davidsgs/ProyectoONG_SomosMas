package com.restteam.ong.controllers.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentBodyDTO {
    @NotBlank
    private String body;
}
