package com.restteam.ong.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    private String name;
    private String facebookUrl;
    private String instagramUrl;
    private String linkedinUrl;
    private String image;
    private String description;
}
