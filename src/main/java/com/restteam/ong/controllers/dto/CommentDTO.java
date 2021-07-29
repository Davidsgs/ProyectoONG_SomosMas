package com.restteam.ong.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CommentDTO {
    @NotBlank
    private String body;
    @NotNull
    private NewsDTO newsDTO;
}
