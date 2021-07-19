package com.restteam.ong.repositories;

import java.util.Optional;

import com.restteam.ong.models.Categories;

import org.springframework.data.repository.CrudRepository;

public interface CategoriesRepository extends CrudRepository<Categories, Long> {

   public Optional<Categories> findByName(String name);

   public Boolean existsByName(String name);
}
