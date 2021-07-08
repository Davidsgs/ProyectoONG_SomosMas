package com.restteam.ong.controllers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
}
