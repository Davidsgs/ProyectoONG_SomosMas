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

import com.restteam.ong.services.util.CategoriesNotFoundException;
import com.restteam.ong.services.util.EmptyRepositoryException;
import com.restteam.ong.services.util.PageEmptyException;
import com.restteam.ong.util.BindingResultsErrors;
import io.swagger.v3.oas.annotations.media.Schema;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    CategoriesService categoriesService;

    ModelMapper modelMapper = new ModelMapper();

    ////// GetMapping
   /* @GetMapping
    public ResponseEntity<?> getCategories(){

        try{
        ArrayList<Categories> categories = (ArrayList<Categories>) this.categoriesService.getCategories();
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
    }*/
////GetMappingByID
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Long id) {
        if (this.categoriesService.existCategory(id)) {
            Categories category = this.categoriesService.getCategoryById(id);
            return ResponseEntity.status(HttpStatus.OK).body(category);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category " + id + " not found!");
        }
    }

    ////// PostMapping
    @PostMapping
    public ResponseEntity<?> createCategory(
            @Valid @RequestBody CategoryRequest category,
            @Schema(hidden = true) BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return BindingResultsErrors.getResponseEntityWithErrors(bindingResult);
        }
        ResponseEntity response;
        try{
            response = ResponseEntity.ok(categoriesService.postCategory(category));
        }catch(Error e){
            response = ResponseEntity.status(HttpStatus.CONFLICT).body("The data entered may be in conflict. try again!");
        }
        return response;
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
    public ResponseEntity<?> updateCategory(@PathVariable("id") Long id, @Valid @RequestBody CategoryRequest category,
                                            @Schema(hidden = true) BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return BindingResultsErrors.getResponseEntityWithErrors(bindingResult);
        }
            ResponseEntity response;
            try{
                response = ResponseEntity.ok(categoriesService.updateCategory(category,id));
            }catch(Error e){
                response = ResponseEntity.status(HttpStatus.CONFLICT).body("The data entered may be in conflict. try again!");
            }
            return response;
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
    ////Paginacion
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(name = "page",required = true) Integer page) throws Exception {
        try {
            return ResponseEntity.ok(categoriesService.getCategories(page));
        }catch (EmptyRepositoryException e){
            return (ResponseEntity.ok(e.getMessage()));
        }catch (PageEmptyException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


}
