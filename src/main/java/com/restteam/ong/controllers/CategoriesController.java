package com.restteam.ong.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.restteam.ong.controllers.dto.CategoryNameDetailResponse;
import com.restteam.ong.controllers.dto.CategoryRequest;
import com.restteam.ong.models.Categories;
import com.restteam.ong.services.CategoriesService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    ////// GetMapping

    @GetMapping
    public ResponseEntity<?> getCategories(){
    
        try{
        ArrayList<Categories> categories =  this.categoriesService.getCategories();
        if(categories.size()==0){
            return ResponseEntity.status(HttpStatus.OK).body("Category table is empty");
        }
        ArrayList<CategoryNameDetailResponse> categoriesNames= new ArrayList<>();

        for(int i=0; i<categories.size(); i++){
            CategoryNameDetailResponse aux= new CategoryNameDetailResponse();
            modelMapper.map(categories.get(i), aux);
            categoriesNames.add(aux);
        }
        return ResponseEntity.status(HttpStatus.OK).body(categoriesNames);
    }catch(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error. Contact support");
    }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Long id) {
        if (this.categoriesService.existCategory(id)) {
            Categories category = this.categoriesService.getCategoryById(id).get();
            return ResponseEntity.status(HttpStatus.OK).body(category);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category " + id + " not found!");
        }
    }

    ////// PostMapping
    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequest category) {

        Optional<Categories> existCategory = this.categoriesService.getCategoriesByName(category.getName());

        if (existCategory != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This Category already exists!");
        } else {
            Categories newCategory = new Categories();
            modelMapper.map(category, newCategory);
            newCategory.setDeleted(false);
            newCategory.setRegDate(new Date().getTime());
            newCategory.setUpDateDate(newCategory.getRegDate());

            Categories myCategory = this.categoriesService.postCategory(newCategory);

            return ResponseEntity.ok(myCategory);
        }

    }

    ///////// DeleteMapping
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id) {
        if (this.categoriesService.existCategory(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(this.categoriesService.deleteCategoriesById(id));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(this.categoriesService.deleteCategoriesById(id));
        }
    }

    /////////PutMapping
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") Long id, @RequestBody CategoryRequest category){
        if(this.categoriesService.existCategory(id)){
            Categories newCategory= new Categories();
            Categories oldCategory= this.categoriesService.getCategoryById(id).get();
            modelMapper.map(category, newCategory);
            newCategory.setId(id);
            newCategory.setUpDateDate(new Date().getTime());
            newCategory.setRegDate(oldCategory.getRegDate());
            newCategory.setDeleted(oldCategory.getDeleted());
            newCategory.setDescription(oldCategory.getDescription());
            newCategory.setNews(oldCategory.getNews());

            try{
                return ResponseEntity.status(HttpStatus.OK).body(this.categoriesService.postCategory(newCategory));
            }catch(Error e){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("The data entered may be in conflict. try again!");
            }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found. try another one!");
        }
    }

    //////////////////// @Valid
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
