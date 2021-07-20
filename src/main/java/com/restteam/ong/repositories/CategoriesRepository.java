package com.restteam.ong.repositories;

import java.util.Optional;

import com.restteam.ong.models.Categories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {
   Optional<Categories> findByName(String name);
   Optional<Categories> findByDescription(String description);
   Optional<Categories >findByImage (String image);
   Page <Categories> findAll(Pageable pageable);



   public Boolean existsByName(String name);
}
