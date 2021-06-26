package com.restteam.ong.services.impl;

import com.restteam.ong.models.Organization;
import com.restteam.ong.repositories.OrganizationRepository;
import com.restteam.ong.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    OrganizationRepository repository;

    @Override
    public Long create(Organization organization) {
        try {
            if (Objects.isNull(organization.getId())) {
                long timestamp = System.currentTimeMillis() / 1000;
                organization.setCreatedAt(timestamp);
                organization.setUpdatedAt(timestamp);
                Organization newOrganization = repository.save(organization);
                return newOrganization.getId();
            }
            throw new IllegalStateException("The Organization can't be no Null");
        } catch(Exception e){
            throw new IllegalStateException("Unexpected error creating an organization");
        }
    }

    @Override
    public List<Organization> getAll() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new IllegalStateException("Unexpected error getting organizations");
        }
    }

    @Override
    public Long update(Organization organization) {
        try {
            Optional<Organization> organization1 = repository.findById(organization.getId());
            if (organization1.isPresent()) {
                organization.setUpdatedAt(System.currentTimeMillis() / 1000);//podria ser un utils
                return repository.save(organization).getId();
            }
            throw new IllegalStateException("The Organization is not present");
        } catch(Exception e){
            throw new IllegalStateException("Unexpected error updating an organization");
        }
    }

    @Override
    public Boolean delete(Long id) {
        try {
            Optional<Organization> organization = repository.findById(id);
            if (organization.isPresent()) {
                repository.deleteById(id);
                return Boolean.TRUE;
            }
            throw new IllegalStateException("The Organization is not present");
        } catch(Exception e){
            throw new IllegalStateException("Unexpected error deleting an organization");
        }
    }
}
