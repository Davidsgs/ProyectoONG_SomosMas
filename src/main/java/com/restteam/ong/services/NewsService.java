package com.restteam.ong.services;

import com.restteam.ong.controllers.dto.NewsDTO;
import com.restteam.ong.models.News;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface NewsService {


    public News postNews(News news);

    public String deleteNewsById(Long id);

    public News updateNews(NewsDTO news, Long id);

    public Boolean existId(Long id);
    public News getNewsById(Long id);
    public Optional<News> getNewsByName(String name);
}
