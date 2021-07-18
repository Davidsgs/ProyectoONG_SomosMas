package com.restteam.ong.repositories;

import com.restteam.ong.models.Slide;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface SlideRepository extends CrudRepository<Slide, Long> {

    Optional<Slide> findTopByOrderByIdDesc();

    public abstract ArrayList<Slide> findByOrganizationId_IdOrderByNumberOrderAsc(Long organizationId);
}
