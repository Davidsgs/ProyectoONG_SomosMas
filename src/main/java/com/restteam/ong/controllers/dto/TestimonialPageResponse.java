package com.restteam.ong.controllers.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class TestimonialPageResponse {
    private ArrayList<TestimonialDto> testimonials;
    private String previousPageUrl;
    private String nextPageUrl;
}
