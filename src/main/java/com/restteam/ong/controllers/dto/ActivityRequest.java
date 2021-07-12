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
public class ActivityRequest {


    @NotBlank(message = "Please provide name")
    private String name;

    @NotBlank(message = "Please provide content")
    private String content;

    private String image;

}
