package com.restteam.ong.controllers.dto;

import com.restteam.ong.models.Categories;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String content;
    @NotBlank
    private String image;
    @NotNull
    private CategoryRequest categoryRequest;

}