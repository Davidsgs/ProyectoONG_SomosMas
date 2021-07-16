package com.restteam.ong.controllers.dto;

import java.util.ArrayList;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.restteam.ong.models.Slide;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrganizationDTO {
    // name, image, phone y address de la organización
    @NotEmpty
    private String name;
    @NotEmpty
    private String image;
    @NotNull
    private Integer phone;
    @NotEmpty
    private String address;

    private String facebookUrl;

    private String instagramUrl;

    private String linkedinUrl;

    private ArrayList<Slide> slides;
}
