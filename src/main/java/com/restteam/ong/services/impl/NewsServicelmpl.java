package com.restteam.ong.services.impl;

import java.util.Optional;

import com.restteam.ong.controllers.dto.NewsDTO;
import com.restteam.ong.models.Categories;
import com.restteam.ong.models.News;
import com.restteam.ong.repositories.NewsRepository;
import com.restteam.ong.services.NewsService;

import org.modelmapper.ModelMapper;
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
    public News getNewsByName(String name) {
        return newsRepository.findByName(name).orElseThrow(
                () -> new IllegalStateException(String.format("News with name: %s not found.",name))
        );
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

    @Override
    public News findByNameOrElseCreateNewNews(NewsDTO newsDTO){
        var modelMapper = new ModelMapper();
        var news = newsRepository.findByName(newsDTO.getName())
                .orElse(modelMapper.map(newsDTO,News.class));
        news.setCategories(
                modelMapper.map(newsDTO.getCategoryRequest(), Categories.class)
        );
        return news;
    }
}
