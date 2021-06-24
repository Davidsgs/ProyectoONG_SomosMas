package com.restteam.ong.repositories;

import com.restteam.ong.models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoriesRepository extends JpaRepository<Categories,Long> {

}
