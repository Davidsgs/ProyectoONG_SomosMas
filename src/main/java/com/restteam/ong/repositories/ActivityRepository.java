package com.restteam.ong.repositories;

import java.util.Optional;

import com.restteam.ong.models.Activity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends CrudRepository<Activity, Long> {
    public abstract Optional<Activity> findByName(String name);
}
