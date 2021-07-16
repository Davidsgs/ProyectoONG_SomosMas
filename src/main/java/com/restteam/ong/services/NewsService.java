package com.restteam.ong.services;

import com.restteam.ong.controllers.dto.NewsDTO;
import com.restteam.ong.models.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface NewsService {

    public News postNews(News news);

    public String deleteNewsById(Long id);

    public News updateNews(NewsDTO newsDTO, Long id);

    public Boolean existId(Long id);
    public News getNewsById(Long id);
    public News getNewsByName(String name);

    public News findByNameOrElseCreateNewNews(NewsDTO newsDTO);

    public NewsDTO getNewsDTO(Long id);


}
