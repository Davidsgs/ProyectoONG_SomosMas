package com.restteam.ong.repositories;

import com.restteam.ong.models.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role,Long> {
    Optional<Role> findByName(String name);

}
