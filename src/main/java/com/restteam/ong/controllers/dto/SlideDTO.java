package com.restteam.ong.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SlideDTO {

    @Schema(hidden = true)
    private Long id;
    @NotBlank
    private String imageUrl;
    @NotBlank
    private String text;

    private Integer numberOrder;



}
