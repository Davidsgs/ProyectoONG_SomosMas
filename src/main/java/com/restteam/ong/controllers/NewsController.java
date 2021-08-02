package com.restteam.ong.controllers;

import static com.restteam.ong.controllers.OrganizationController.UNEXPECTED_ERROR;

import javax.validation.Valid;

import com.restteam.ong.controllers.dto.CommentBodyResponse;
import com.restteam.ong.controllers.dto.NewsDTO;
import com.restteam.ong.controllers.dto.NewsResponseDTO;
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
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<?> postNews(@Valid @RequestBody NewsDTO newsDTO,
            @Parameter(hidden = true) BindingResult bindingResult) {
        ResponseEntity<?> response;

        if (bindingResult.hasErrors()) { // Se revisa si no hay errores del @Valid.
            response = BindingResultsErrors.getResponseEntityWithErrors(bindingResult); // Se mandan a traer un
                                                                                        // ResponseEntity que contenga
                                                                                        // los errores.
        }
        // Si no hay errores entonces:
        else {
            if(categoriesService.existCategoryByName(newsDTO.getCategoryName())){
                newsService.postNews(newsDTO);
                response = ResponseEntity.status(HttpStatus.OK).body("News saved successfully");
            }else{
                return ResponseEntity.badRequest().body("The category must exist");
            }
            
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
        ResponseEntity<?> response;
        try {
            categoriesService.getCategoryByName(newsDTO.getCategoryName());
            newsService.updateNews(newsDTO, id);
            response = ResponseEntity.status(HttpStatus.OK).body("News update successfuly");
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
            NewsResponseDTO response= new NewsResponseDTO();
            modelMapper.map(newsDTO, response);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("news " + id + " no encontrada!");
        }
    }

    @GetMapping()
    public ResponseEntity<?> getNews(@RequestParam Integer page) {
        try {
            return ResponseEntity.ok(newsService.getAll(page));
        } catch (IllegalStateException i) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(i.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(UNEXPECTED_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param id = El id del news que quieras saber los comentarios.
     * @return ResponseEntity con la lista de Comentarios.
     */

    @GetMapping("/{id}/comments")
    public ResponseEntity<?> getCommentsOfNewsWithId(@PathVariable("id") Long id) {
        ResponseEntity responseEntity;
        try {
            var news = newsService.getNewsById(id);
            var commentsOfNews = news.getComments();
            // Mapeamos los comentarios a un DTO para ser devueltos.
            var commentsBodyDTO = commentsOfNews.stream()
                    .map(comment -> modelMapper.map(comment, CommentBodyResponse.class));
            responseEntity = ResponseEntity.ok(commentsBodyDTO);
        } catch (IllegalStateException e) {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            responseEntity = ResponseEntity.badRequest().body(e.getMessage());
        }
        return responseEntity;
    }

}
