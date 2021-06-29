package com.restteam.ong.repositories;

import com.restteam.ong.models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CategoriesRepository extends JpaRepository<Categories,Long> {

    Optional<Categories> findByName(String name);
}