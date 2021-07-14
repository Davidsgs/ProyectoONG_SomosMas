package com.restteam.ong.controllers.dto;

import com.restteam.ong.models.News;
import com.restteam.ong.models.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommentDTO {
    @NotBlank
    private String body;
    @NotNull
    private NewsDTO newsDTO;
}
