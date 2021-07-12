package com.restteam.ong.controllers.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsDTO {

    private String name;

    private String content;

    private String image;
    @NotNull
    private CategoryRequest categoryRequest;

}