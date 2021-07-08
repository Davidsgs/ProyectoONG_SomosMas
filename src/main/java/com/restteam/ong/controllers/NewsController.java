package com.restteam.ong.controllers;


import com.restteam.ong.controllers.dto.NewsDTO;

import com.restteam.ong.models.Categories;
import com.restteam.ong.models.News;

import com.restteam.ong.services.impl.NewsServicelmpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;


@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    NewsServicelmpl newsService;
    ModelMapper modelMapper = new ModelMapper();

    @PostMapping

    public ResponseEntity<News> postNews( @Valid @RequestBody NewsDTO newsDTO) {

        News news = new News();
        Categories categories = new Categories();
        modelMapper.map(newsDTO.getCategoryRequest(),categories);
        modelMapper.map(newsDTO, news);

        news.setRegDate(new Date().getTime()/1000);
        news.setUpDateDate(news.getRegDate());
        news.setCategories(categories);


        News newNews = this.newsService.postNews(news);

        return new ResponseEntity(newNews,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable("id") Long id){
        if (this.newsService.existId(id)){
            return ResponseEntity.status(HttpStatus.OK).body(this.newsService.deleteNewsById(id));
        }
        else{
            return   ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se encontro la novedad con esa id intente de nuevo");
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putNews(@PathVariable("id") Long id,@RequestBody NewsDTO news){
        if (this.newsService.existId(id)) {
            News newNews= new News();
            News oldNews = this.newsService.getNewsById(id).get();
            modelMapper.map(news,newNews);
            newNews.setId(id);
            newNews.setUpDateDate(new Date().getTime());
            newNews.setRegDate(oldNews.getRegDate());
            newNews.setCategories(oldNews.getCategories());
            newNews.setDeleted(oldNews.getDeleted());
            return ResponseEntity.status(HttpStatus.OK).body(this.newsService.patchNews(newNews));
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("la id ingresada no se encuentra, intente con otra id");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNewsById(@PathVariable("id") Long id) {
        NewsDTO newsDTO = this.newsService.getNewsDTO(id);
        if (this.newsService.existId(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(newsDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("news " + id + " no encontrada!");
        }
    }

}
