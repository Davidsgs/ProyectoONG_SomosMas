package com.restteam.ong.controllers.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
//Quizas lo borre
@Data
@NoArgsConstructor
public class CategoryPageResponse {
    private ArrayList<CategoryDTO> categoryResponse;
    private String previousPageUrl;
    private String nextPageUrl;

}
