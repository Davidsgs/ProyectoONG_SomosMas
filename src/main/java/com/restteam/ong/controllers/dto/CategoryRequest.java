package com.restteam.ong.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@Data
public class CategoryRequest {

    @NotBlank(message = "please, provide name")
    private String name;

    @NotBlank(message = "please, provide description")
    private String description;

    private String image;

    public CategoryRequest() {
    }

    public CategoryRequest(String name) {
        this.name = name;
    }
}
