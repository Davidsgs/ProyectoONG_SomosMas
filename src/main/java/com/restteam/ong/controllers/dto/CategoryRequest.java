package com.restteam.ong.controllers.dto;

import javax.validation.constraints.NotBlank;

import lombok.*;

@Getter
@Setter
@Data
public class CategoryRequest {

    @NotBlank(message = "please, provide name")
    private String name;

    @NotBlank(message = "please, provide description")
    private String description;

    private String image;
}
