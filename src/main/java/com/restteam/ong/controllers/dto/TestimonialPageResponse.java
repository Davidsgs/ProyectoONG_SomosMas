package com.restteam.ong.controllers.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class TestimonialPageResponse {
    private ArrayList<TestimonialDto> testimonials;
    private String previousPageUrl;
    private String nextPageUrl;
}
