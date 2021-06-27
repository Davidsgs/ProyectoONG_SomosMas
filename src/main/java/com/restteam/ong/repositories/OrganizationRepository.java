package com.restteam.ong.repositories;

import com.restteam.ong.models.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization,Long> {
   List<Organization> findAll();
}
