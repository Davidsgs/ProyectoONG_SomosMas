package com.restteam.ong.repositories;

import com.restteam.ong.models.Testimonial;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface TestimonialRepository extends CrudRepository<Testimonial,Long> {

    Optional<Testimonial> findByName(String name);
    Optional<Testimonial> findByContent(String content);
}
