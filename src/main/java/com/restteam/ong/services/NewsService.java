package com.restteam.ong.services;

import com.restteam.ong.models.Categories;
import com.restteam.ong.models.News;
import com.restteam.ong.repositories.CategoriesRepository;
import com.restteam.ong.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NewsService {
    @Autowired
    NewsRepository newsRepository;

    public News postNews(News news){
        return newsRepository.save(news);
    }

    public String deleteNewsById(Long id) {
        try {
            newsRepository.deleteById(id);
            return "La noticia se elimino con la id : " + id;
        } catch (Exception error) {
            return "No existe la noticia con esa id, ingrese otro";
        }
    }
    public String patchNews(News news) {
        if(newsRepository.existsById(news.getId())) {
            newsRepository.save(news);
            return "La noticia de modifico con la id "+ news.getId();
        }
        return "No puedo modificar la noticia porque no se encontró en la base de datos, inténtelo de nuevo";
    }

    public Optional<News> getnewsByName(String name) {
        Optional<News> news = newsRepository.findByName(name);
        if(news != null && news.isPresent()){
            return news;
        }
        return null;
    }
}
