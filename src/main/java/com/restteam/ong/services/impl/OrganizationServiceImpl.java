package com.restteam.ong.services.impl;

import java.util.List;
import java.util.Optional;

import com.restteam.ong.controllers.dto.OrganizationCreateDTO;
import com.restteam.ong.controllers.dto.OrganizationDTO;
import com.restteam.ong.models.Organization;
import com.restteam.ong.repositories.OrganizationRepository;
import com.restteam.ong.services.OrganizationService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    OrganizationRepository repository;
    ModelMapper modelMapper = new ModelMapper();

    public OrganizationServiceImpl(OrganizationRepository orgRepo) {
        this.repository = orgRepo;
    }

    @Override
    public Long create(OrganizationCreateDTO dto) {
        try {
            long timestamp = System.currentTimeMillis() / 1000;
            Organization organization = modelMapper.map(dto, Organization.class);
            organization.setCreatedAt(timestamp);
            organization.setUpdatedAt(timestamp);
            Organization newOrganization = repository.save(organization);
            return newOrganization.getId();
        } catch (Exception e) {
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
    public Long update(OrganizationDTO dto, Long id) {
        Optional<Organization> organizationOptional = repository.findById(id);
        if (organizationOptional.isPresent()) {
            Organization organization = organizationOptional.get();
            updateOrganization(dto, organization);
            return repository.save(organization).getId();
        }
        throw new IllegalStateException("The Organization is not present");
    }

    private void updateOrganization(OrganizationDTO dto, Organization organization) {
        organization.setName(dto.getName());
        organization.setImage(dto.getImage());
        organization.setAddress(dto.getAddress());
        organization.setPhone(dto.getPhone());
        organization.setFacebookUrl(dto.getFacebookUrl());
        organization.setInstagramUrl(dto.getInstagramUrl());
        organization.setLinkedinUrl(dto.getLinkedinUrl());
        organization.setUpdatedAt(System.currentTimeMillis() / 1000);// podria ser un utils
    }

    @Override
    public Boolean delete(Long id) {
        try {

            if (repository.existsById(id)) {
                repository.deleteById(id);
                return Boolean.TRUE;
            }
            throw new IllegalStateException("The Organization is not present");
        } catch (Exception e) {
            throw new IllegalStateException("Unexpected error deleting an organization");
        }
    }

    @Override
    public OrganizationDTO getDetail(Long id) {
        Optional<Organization> organization = repository.findById(id);
        if (organization.isPresent()) {
            OrganizationDTO organizationDTO = modelMapper.map(organization.get(), OrganizationDTO.class);
            return organizationDTO;
        }
        throw new IllegalStateException("The Organization is not present");
    }

    public OrganizationServiceImpl() {
    }

    @Override
    public Organization getOrganization(Long id) {
        return this.repository.findById(id).get();
    }
}
