package com.restteam.ong.controllers;

import java.util.Date;

import javax.validation.Valid;

import com.restteam.ong.controllers.dto.NewsDTO;
import com.restteam.ong.models.News;
import com.restteam.ong.services.CategoriesService;
import com.restteam.ong.services.impl.NewsServicelmpl;
import com.restteam.ong.util.BindingResultsErrors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    NewsServicelmpl newsService;
    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    CategoriesService categoriesService;

    @PostMapping
    public ResponseEntity<News> postNews(@Valid @RequestBody NewsDTO newsDTO,
                                         @Parameter(hidden = true) BindingResult bindingResult) {
        ResponseEntity response;

        if(bindingResult.hasErrors()){ //Se revisa si no hay errores del @Valid.
            response = BindingResultsErrors.getResponseEntityWithErrors(bindingResult); //Se mandan a traer un ResponseEntity que contenga los errores.
        }
        //Si no hay errores entonces:
        else {
            response = new ResponseEntity<>(newsService.postNews(newsDTO), HttpStatus.OK);
        }

        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable("id") Long id) {
        if (this.newsService.existId(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(this.newsService.deleteNewsById(id));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("no se encontro la novedad con esa id intente de nuevo");
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putNews(@PathVariable("id") Long id, @RequestBody NewsDTO newsDTO) {
        ResponseEntity response;
        try {
            response = ResponseEntity.ok(newsService.updateNews(newsDTO, id));
        } catch (IllegalStateException e) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return response;
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

    @GetMapping("/{id}/comments")
    public ResponseEntity<?> getCommentsOfNewsWithId(@PathVariable("id") Long id){
        ResponseEntity responseEntity;
        try{
            var news = newsService.getNewsById(id);
            var commentsOfNews = news.getComments();
            responseEntity = ResponseEntity.ok(commentsOfNews);
        }catch (IllegalStateException e){
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            responseEntity = ResponseEntity.badRequest().body(e.getMessage());
        }
        return responseEntity;
    }

}
