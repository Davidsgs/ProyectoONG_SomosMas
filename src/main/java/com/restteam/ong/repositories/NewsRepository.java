package com.restteam.ong.repositories;

import java.util.List;
import java.util.Optional;

import com.restteam.ong.models.News;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {

    Optional<News> findByName(String name);

    @Override
    List<News> findAll();
}
