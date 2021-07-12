package com.restteam.ong.services.impl;

import java.util.Optional;

import com.restteam.ong.controllers.dto.NewsDTO;
import com.restteam.ong.models.News;
import com.restteam.ong.repositories.NewsRepository;
import com.restteam.ong.services.NewsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class NewsServicelmpl implements NewsService {
    @Autowired
    NewsRepository newsRepository;

    @Override
    public News postNews(News news) {

        return newsRepository.save(news);

    }

    @Override
    public String deleteNewsById(Long id) {
        try {
            newsRepository.deleteById(id);
            return "La noticia se elimino con la id : " + id;
        } catch (Exception error) {
            return "No existe la noticia con esa id, ingrese otro";
        }
    }

    @Override
    public String patchNews(News news) {
        if (this.existId(news.getId())) {

            newsRepository.save(news);
            return "se pudo modificar la novedad con id ingresada";
        } else {
            return "no se pudo modificar la novedad con la id ingresada";
        }
    }

    @Override
    public Optional<News> getNewsByName(String name) {
        Optional<News> news = newsRepository.findByName(name);
        if (news != null && news.isPresent()) {
            return news;
        }
        return null;
    }

    @Override
    public Boolean existId(Long id) {
        return newsRepository.existsById(id);
    }

    @Override
    public Optional<News> getNewsById(Long id) {

        return newsRepository.findById(id);
    }

    public NewsDTO getNewsDTO(Long id) {
        Optional<News> news = this.getNewsById(id);
        if (news != null && news.isPresent()) {
            NewsDTO newsDTO = new NewsDTO();
            newsDTO.setName(news.get().getName());
            newsDTO.setImage(news.get().getImage());
            newsDTO.setContent(news.get().getContent());
            return newsDTO;
        }
        return null;
    }
}
