package com.restteam.ong.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.restteam.ong.controllers.dto.OrganizationCreateDTO;
import com.restteam.ong.controllers.dto.OrganizationDTO;
import com.restteam.ong.models.Organization;
import com.restteam.ong.repositories.OrganizationRepository;
import com.restteam.ong.services.impl.OrganizationServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

public class OrganizationServiceTest {

    ModelMapper modelMapper= new ModelMapper();

    OrganizationRepository orgRepoMok = Mockito.mock(OrganizationRepository.class);
    OrganizationService orgServMok = Mockito.mock(OrganizationServiceImpl.class);
    OrganizationService organizationService = new OrganizationServiceImpl(orgRepoMok);



    Organization org1;
    Organization org2;
    ArrayList<Organization> allOrgs;
    OrganizationCreateDTO orgDTO1;
    OrganizationDTO orgDTO2;
    Organization orgDTO1mapped;

    @BeforeEach
    public void setUp() {

        org1 = new Organization();
        org1.setId(1L);
        org1.setName("name1");
        org1.setImage("./image/img1.jpg");
        org1.setAddress("address");
        org1.setPhone(123456789);
        org1.setEmail("1234@email.com");
        org1.setWelcomeText("welcomeText");
        org1.setAboutUsText("aboutUsText");
        org1.setCreatedAt(new Date().getTime());
        org1.setUpdatedAt(org1.getCreatedAt());
        org1.setFacebookUrl("facebookUrl");
        org1.setInstagramUrl("instagramUrl");
        org1.setLinkedinUrl("linkedinUrl");
        org1.setDeleted(false);

        org2 = new Organization();
        org2.setId(2L);
        org2.setName("name2");
        org2.setImage("./image/img2.jpg");
        org2.setAddress("address");
        org2.setPhone(123456789);
        org2.setEmail("1234@email.com");
        org2.setWelcomeText("welcomeText");
        org2.setAboutUsText("aboutUsText");
        org2.setCreatedAt(new Date().getTime());
        org2.setUpdatedAt(org1.getCreatedAt());
        org2.setFacebookUrl("facebookUrl");
        org2.setInstagramUrl("instagramUrl");
        org2.setLinkedinUrl("linkedinUrl");
        org2.setDeleted(false);


        orgDTO1= new OrganizationCreateDTO();
        orgDTO2= new OrganizationDTO();

        allOrgs= new ArrayList<>();
        allOrgs.add(org1);
        allOrgs.add(org2);
        orgDTO1mapped= new Organization();

        Mockito.when(orgRepoMok.findById(org1.getId())).thenReturn(Optional.of(org1));
        Mockito.when(orgRepoMok.findAll()).thenReturn(allOrgs);
        Mockito.when(orgRepoMok.save(orgDTO1mapped)).thenReturn(org1);
        Mockito.when(orgServMok.update(orgDTO2, org2.getId())).thenReturn(org2.getId());
        Mockito.when(orgServMok.delete(org1.getId())).thenReturn(true);
        Mockito.when(orgServMok.create(orgDTO1)).thenReturn(org1.getId());
    }
////////////REPOSITORY/////////////////

    //+++++findById++++++

    @Test // el ID existe
    public void findByIdTrue() {
        Organization actual = orgRepoMok.findById(org1.getId()).get();
        assertEquals(org1, actual);
    }

    @Test // el ID no existe
    public void findByIdFalse() {
        Optional<Organization> actual = orgRepoMok.findById(4126L);// Variable no presente
        assertEquals(Optional.empty(), actual);
    }

    //++++++findAll++++++

    @Test
    public void findAll(){
        ArrayList<Organization> actual= (ArrayList<Organization>)orgRepoMok.findAll();
        assertEquals(allOrgs, actual);
    }

    //++++++save+++++++++
    @Test
    public void save(){
        modelMapper.map(org1, orgDTO1);//recorta campos irrelevantes para el DTO
        modelMapper.map(orgDTO1, orgDTO1mapped);//id= null
        Organization actual= orgRepoMok.save(orgDTO1mapped);//devuelve todos los campos
        assertEquals(org1, actual);
    }

/////////////SERVICES//////////////

@Test
public void create(){
    modelMapper.map(org1, orgDTO1);
    Long actual= orgServMok.create(orgDTO1);
    assertEquals(org1.getId(), actual);
}

@Test
public void getAll(){
    List<Organization> actual= organizationService.getAll();
    assertEquals(allOrgs, actual);
}

@Test//el ID existe
public void updateTrue(){
    modelMapper.map(org2, orgDTO2);//copia campos y elimina ID
    orgDTO2.setAddress("newAddress");//cambiamos algunas cosas
    orgDTO2.setImage("newImage");
    Long actual= orgServMok.update(orgDTO2, org2.getId());
    assertEquals(org2.getId(), actual);//actualizado correctamente
}


@Test//el ID no existe
public void updateFalse(){
    modelMapper.map(org2, orgDTO2);//copia campos y elimina ID
    orgDTO2.setAddress("newAddress");//cambiamos algunas cosas
    orgDTO2.setImage("newImage");
    Long actual= orgServMok.update(orgDTO2, org1.getId());
    assertNotEquals(org2.getId(), actual);//no se encontró el elemento a actualizar
}

@Test
public void deleteTrue(){
    Boolean actual= orgServMok.delete(org1.getId());
    assertTrue(actual);
}

@Test
public void deletedFalse(){
    Boolean actual= orgServMok.delete(org2.getId());
    assertFalse(actual);
}
}
