package com.restteam.ong.repositories;

import com.restteam.ong.models.Testimonial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
@Repository
public interface TestimonialRepository extends JpaRepository<Testimonial,Long>{

    Optional<Testimonial> findByName(String name);
    Optional<Testimonial> findByContent(String content);
    Page<Testimonial> findAll(Pageable pageable);
}
