package com.restteam.ong.services;

import com.restteam.ong.controllers.dto.NewsDTO;
import com.restteam.ong.models.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface NewsService {

    public News postNews(News news);

    public String deleteNewsById(Long id);

    public String patchNews(News news);


    public Boolean existId(Long id);
    public Optional<News> getNewsById(Long id);
    public News getNewsByName(String name);

    public News findByNameOrElseCreateNewNews(NewsDTO newsDTO);
}
