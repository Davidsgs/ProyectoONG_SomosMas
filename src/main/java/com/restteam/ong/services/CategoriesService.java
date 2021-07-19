package com.restteam.ong.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import com.restteam.ong.controllers.dto.CategoryRequest;
import com.restteam.ong.models.Categories;
import com.restteam.ong.models.News;
import com.restteam.ong.repositories.CategoriesRepository;

import com.restteam.ong.repositories.NewsRepository;
import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CategoriesService {
    @Autowired
    CategoriesRepository categoriesRepository;
    NewsService newsService;

    private final String CATEGORY_NOT_FOUND_NAME = "Category with name: %s not found";

    private final String CATEGORY_NOT_FOUND_ID = "Category with id: %d not found";
//crea una categoria.
    public Categories postCategory(CategoryRequest categories) {
        var newCategory = new Categories(); // crea una variable de clase categoria
        News news;
        newCategory.setDescription(categories.getDescription()); //hace un map de name,image,descripton
        newCategory.setName(categories.getName());
        newCategory.setImage(categories.getImage());
        newCategory.setRegDate(new Date().getTime()); //le agrega los getTime en regDate y upDateDate
        newCategory.setUpDateDate(newCategory.getRegDate());
        return categoriesRepository.save(newCategory); //guarda la categoria creada
    }
//elimina una categoria que recibe por parametro un id
    public String deleteCategoriesById(Long id) {
        try {
            this.categoriesRepository.deleteById(id);
            return "Category " + id + " successfully deleted";
        } catch (Exception e) {
            return "failed to delete Category " + id + "\nERROR\n\n" + e;
        }
    }

//modifica una categoria mediantre un DTO y un id que se envia por parametro
    @Transactional
    public Categories updateCategory(CategoryRequest categoryRequest,Long id) {
        var category = this.getCategoryById(id); //devuelve la categoria que se busca por la id
        category.setName(categoryRequest.getName()); //modifica el name,image,description
        category.setImage(categoryRequest.getImage());
        category.setDescription(categoryRequest.getDescription());
        return category; //devuelve la categoria
    }
// devuelve la categoria mediante el nombre que se recibe por parametro
    public Categories getCategoriesByName(String name) {
        return categoriesRepository.findByName(name).orElseThrow(
                () -> new IllegalStateException(String.format(CATEGORY_NOT_FOUND_NAME, name))
        );
    }
// devuelve una lista de todas las categorias
    public ArrayList<Categories> getCategories() {
        return (ArrayList<Categories>) this.categoriesRepository.findAll();
    }
//devuelve un booleano dependiendo si existe o no la categoria con id que se envia por parametro
    public boolean existCategory(Long id) {
        return this.categoriesRepository.existsById(id);
    }
//devuelve la categoria que tiene como id el que se manda por parametro
    public Categories getCategoryById(Long id) {
        return categoriesRepository.findById(id).orElseThrow(
                () -> new IllegalStateException(String.format(CATEGORY_NOT_FOUND_ID, id))
        );
    }
    public Boolean existCategoryByName(String name){
        return categoriesRepository.existsByName(name);
    }
}