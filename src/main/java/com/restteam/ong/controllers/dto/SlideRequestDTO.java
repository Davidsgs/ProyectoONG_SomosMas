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

public class SlideRequestDTO {

    @NotBlank
    private String imageUrl;

    private String text;

    @NotBlank
    private Long orgId;
}
