package com.restteam.ong.services;

import com.restteam.ong.controllers.dto.OrganizationCreateDTO;
import com.restteam.ong.controllers.dto.OrganizationDTO;
import com.restteam.ong.models.Organization;


import java.util.List;

public interface OrganizationService {
    
   
    Long create(OrganizationCreateDTO dto);
     List<Organization> getAll();
     Long update(OrganizationDTO dto, Long id);
     Boolean delete(Long id);
     OrganizationDTO getDetail(Long id) ;
     Organization getOrganization(Long id);
}
