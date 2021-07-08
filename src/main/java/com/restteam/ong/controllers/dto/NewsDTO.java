package com.restteam.ong.controllers.dto;

import com.restteam.ong.models.Categories;
import lombok.*;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
@Getter
@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsDTO {

    private String name;

    private String content;

    private String image;

    private CategoryRequest categoryRequest;





}