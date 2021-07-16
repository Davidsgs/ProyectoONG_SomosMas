package com.restteam.ong.controllers.dto;

import com.restteam.ong.models.Organization;
import com.restteam.ong.models.Slide;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
