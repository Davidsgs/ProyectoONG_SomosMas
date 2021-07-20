package com.restteam.ong.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class NewsPageDTO {

      List<NewsDTO> elements;

      String nextUrl;

      String previousUrl;

      Long totalElements;

      Integer currentPage;
}
