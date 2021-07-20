package com.restteam.ong.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import com.restteam.ong.controllers.dto.CategoryDTO;
import com.restteam.ong.controllers.dto.CategoryPageResponse;
import com.restteam.ong.controllers.dto.CategoryRequest;
import com.restteam.ong.controllers.dto.TestimonialDto;
import com.restteam.ong.models.Categories;
import com.restteam.ong.models.News;
import com.restteam.ong.models.Testimonial;
import com.restteam.ong.repositories.CategoriesRepository;

import com.restteam.ong.repositories.NewsRepository;
import com.restteam.ong.services.util.EmptyRepositoryException;
import com.restteam.ong.services.util.PageEmptyException;
import org.hibernate.mapping.Set;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CategoriesService {
    @Autowired
    CategoriesRepository categoriesRepository;
    NewsService newsService;
    ModelMapper modelMapper = new ModelMapper();
    private final String CATEGORY_NOT_FOUND_NAME = "Category with name: %s not found";

    private final String CATEGORY_NOT_FOUND_ID = "Category with id: %d not found";
//crea una categoria.
    public Categories postCategory(CategoryRequest categories) {
        var newCategory = new Categories(); // crea una variable de clase categoria
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
    public CategoryPageResponse getCategories(Integer page) throws EmptyRepositoryException, PageEmptyException {
       if (categoriesRepository.findAll().isEmpty()){
           throw  new EmptyRepositoryException("there are 0 registers of Categories");

       }
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<Categories> categoriesPage= categoriesRepository.findAll(pageRequest);
        if (categoriesPage.getContent().isEmpty()){
            throw  new PageEmptyException(String.format("Page not found, there only %s page",categoriesPage.getTotalPages()));
        }
        ArrayList<CategoryDTO> categoryDTOArrayList= new ArrayList<>();
        categoriesPage.forEach((Categories cata)-> categoryDTOArrayList.add(mapToDto(cata)));
        CategoryPageResponse categoryPageResponse = new CategoryPageResponse();
        categoryPageResponse.setCategoryResponse(categoryDTOArrayList);
        if (categoriesPage.hasNext()){
            categoryPageResponse.setNextPageUrl(String.format("/category=%s", page + 1));

        }
        if (categoriesPage.hasPrevious()){
            categoryPageResponse.setPreviousPageUrl(String.format("/category?page=%s", page-1));

        }
        return categoryPageResponse;
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
    public Categories getCategoriesByNameOrCreateNew(CategoryRequest categoryRequest){
        Categories categories;
        ModelMapper modelMapper = new ModelMapper();
        try{
            categories = this.getCategoriesByName(categoryRequest.getName());
        }catch (Exception e){
            categories = modelMapper.map(categoryRequest, Categories.class);
            categories.setRegDate(System.currentTimeMillis() / 1000);
            categories.setUpDateDate(categories.getRegDate());
            categories = categoriesRepository.save(categories);
        }
        return categories;
    }
    private CategoryDTO mapToDto(Categories categories) {
        return modelMapper.map(categories, CategoryDTO.class);
    }
}