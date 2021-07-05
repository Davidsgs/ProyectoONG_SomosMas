package com.restteam.ong.controllers.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class OrganizationCreateDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String image;

    private String address;

    private Integer phone;

    @NotBlank
    private String email;

    @NotBlank
    private String welcomeText;

    private String aboutUsText;

    private String facebookUrl;

    private String instagramUrl;

    private String linkedinUrl;



}
