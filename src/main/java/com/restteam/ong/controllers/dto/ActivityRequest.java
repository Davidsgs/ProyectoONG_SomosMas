package com.restteam.ong.controllers.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityRequest {

    private Long id;

    @NotNull(message = "Please provide name")
    private String name;

    @NotNull(message = "Please provide content")
    private String content;

    private String image;

}
