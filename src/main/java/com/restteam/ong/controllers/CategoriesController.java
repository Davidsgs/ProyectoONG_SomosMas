package com.restteam.ong.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.restteam.ong.controllers.dto.CategoryRequest;
import com.restteam.ong.models.Categories;
import com.restteam.ong.services.CategoriesService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    CategoriesService categoriesService;

    ModelMapper modelMapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<?> getCategories() {
        ArrayList<Categories> categories = new ArrayList<>();
        // try{
        categories = this.categoriesService.getCategories();
        // }catch(Exception e){
        // return ResponseEntity.badRequest().build();
        // }
        ArrayList<String> categoriesNames = new ArrayList<>();

        for (int i = 0; i < categories.size(); i++) {
            categoriesNames.add(categories.get(i).getName());
        }
        return ResponseEntity.ok(categoriesNames);
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequest category) {

        Optional<Categories> existCategory = this.categoriesService.getCategoriesByName(category.getName());

        if (existCategory != null) {
            String body = "{\n\"category name\" : \"" + category.getName()
                    + "\",\n\"message\" : \"This category already exists\"\n }";
            return ResponseEntity.badRequest().body(body);
        } else {
            Categories newCategory = new Categories();
            modelMapper.map(category, newCategory);
            newCategory.setDeleted(false);
            newCategory.setRegDate(new Date().getTime());
            newCategory.setUpDateDate(new Date().getTime());

            Categories myCategory = this.categoriesService.postCategory(newCategory);

            return ResponseEntity.ok(myCategory);
        }

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
