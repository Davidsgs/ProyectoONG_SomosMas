package com.restteam.ong.services;

import java.util.ArrayList;
import java.util.Optional;

import com.restteam.ong.models.Categories;
import com.restteam.ong.repositories.CategoriesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriesService {
    @Autowired
    CategoriesRepository categoriesRepository;

    public Categories postCategory(Categories categories) {
        return categoriesRepository.save(categories);
    }

    public String deleteCategoriesById(Long id) {
        try {
            this.categoriesRepository.deleteById(id);
            return "Category " + id + " successfully deleted";
        } catch (Exception e) {
            return "failed to delete Category " + id + "\nERROR\n\n" + e;
        }
    }

    public String patchCategories(Categories categories) {
        if (categoriesRepository.existsById(categories.getId())) {
            categoriesRepository.save(categories);
            return "La categoría fue modificada con id " + categories.getId();
        }
        return "No puedo modificar la categoría porque no se encontró en la base de datos, inténtelo de nuevo";
    }

    public Optional<Categories> getCategoriesByName(String name) {
        Optional<Categories> categories = categoriesRepository.findByName(name);
        if (categories != null && categories.isPresent()) {
            return categories;
        }
        return null;
    }

    public ArrayList<Categories> getCategories() {
        return (ArrayList<Categories>) this.categoriesRepository.findAll();
    }

    public boolean existCategory(Long id) {
        return this.categoriesRepository.existsById(id);
    }

    public Optional<Categories> getCategoryById(Long id) {
        return this.categoriesRepository.findById(id);
    }
}
