package com.restteam.ong.services;

import com.restteam.ong.controllers.dto.OrganizationDTO;
import com.restteam.ong.models.Organization;

import java.util.List;

public interface OrganizationService {
    public Long create(Organization organization);
    public List<Organization> getAll();
    public Long update(OrganizationDTO dto, Long id);
    public Boolean delete(Long id);
    public OrganizationDTO getDetail(Long id) ;
}
