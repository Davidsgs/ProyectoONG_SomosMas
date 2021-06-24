package com.restteam.ong.repositories;

import com.restteam.ong.models.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News,Long> {
    
}
