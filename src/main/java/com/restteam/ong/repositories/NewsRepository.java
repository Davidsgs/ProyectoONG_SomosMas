package com.restteam.ong.repositories;

import com.restteam.ong.models.Categories;
import com.restteam.ong.models.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsRepository extends JpaRepository<News,Long> {

    Optional<News> findByName(String name);
}
