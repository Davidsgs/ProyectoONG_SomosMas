package com.restteam.ong.services;

import com.restteam.ong.models.Categories;
import com.restteam.ong.repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CategoriesService {
    @Autowired
    CategoriesRepository categoriesRepository;

    public Categories postCategory(Categories categories){
        return categoriesRepository.save(categories);
    }

    public String deleteCategoriesById(Long id) {
        try {
            categoriesRepository.deleteById(id);
            return "la categoría fue eliminada con la id: " + id;
        } catch (Exception error) {
            return "No se encontró ninguna categoría con esa id, ingrese una id existente";
        }
    }
    public String patchCategories(Categories categories) {
        if(categoriesRepository.existsById(categories.getId())) {
            categoriesRepository.save(categories);
            return "La categoría fue modificada con id "+ categories.getId();
        }
        return "No puedo modificar la categoría porque no se encontró en la base de datos, inténtelo de nuevo";
    }

    public Optional<Categories> getCategoriesByName(String name) {
        Optional<Categories> categories = categoriesRepository.findByName(name);
        if(categories != null && categories.isPresent()){
            return categories;
        }
        return null;
    }

    public ArrayList<Categories> getCategories(){
        return (ArrayList<Categories>)this.categoriesRepository.findAll();
    }

    public boolean existCategory(Long id){
        return this.categoriesRepository.existsById(id);
    }

    public Optional<Categories> getCategoryById(Long id){
        return this.categoriesRepository.findById(id);
    }
}
