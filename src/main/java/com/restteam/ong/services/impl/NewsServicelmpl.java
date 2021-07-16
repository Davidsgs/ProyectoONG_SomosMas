package com.restteam.ong.services.impl;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restteam.ong.controllers.dto.NewsDTO;
import com.restteam.ong.models.Categories;
import com.restteam.ong.models.News;
import com.restteam.ong.repositories.NewsRepository;
import com.restteam.ong.services.NewsService;

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

    @Transactional
    public News updateNews(NewsDTO news, Long id) {
        var newsToUpdate = getNewsById(id);
        if (news.getCategories() != null) {
            newsToUpdate.setCategories(news.getCategories());
        }
        if (news.getContent() != null && !news.getContent().isBlank()) {
            newsToUpdate.setContent(news.getContent());
        }
        if (news.getName() != null && !news.getName().isBlank()) {
            newsToUpdate.setName(news.getName());
        }
        if (news.getImage() != null && !news.getImage().isBlank()) {
            newsToUpdate.setImage(news.getImage());
        }
        //Se agrega la ultima vez que fue actualizado.
        newsToUpdate.setUpDateDate(System.currentTimeMillis() / 1000);
        return newsToUpdate;
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
    public News getNewsById(Long id) {
        return newsRepository.findById(id).orElseThrow(
                () -> new IllegalStateException(String.format("News with ID %n doesn't exist",id))
        );
    }

    public NewsDTO getNewsDTO(Long id) {
        var newsDTO = new NewsDTO();
        try{
            var news = this.getNewsById(id);
            newsDTO.setName(news.getName());
            newsDTO.setImage(news.getImage());
            newsDTO.setContent(news.getContent());
        }catch (Exception e){
            throw new IllegalStateException(e.getMessage());
        }
        return newsDTO;
    }

    @Override
    public News findByNameOrElseCreateNewNews(NewsDTO newsDTO){
        var modelMapper = new ModelMapper();
        var news = newsRepository.findByName(newsDTO.getName())
                .orElse(modelMapper.map(newsDTO,News.class));
        news.setCategories(
                modelMapper.map(newsDTO.getCategories(), Categories.class)
        );
        return news;
    }
}
