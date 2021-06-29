package com.restteam.ong.controllers.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

    @NotBlank(message = "please, provide name")
    private String name;

    @NotBlank(message = "please, provide description")
    private String description;

    private String image;
}
