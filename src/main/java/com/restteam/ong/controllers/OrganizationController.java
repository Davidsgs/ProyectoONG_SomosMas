package com.restteam.ong.controllers;


import com.restteam.ong.controllers.dto.OrganizationDTO;
import com.restteam.ong.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/organization/public")
public class OrganizationController {
    @Autowired
    OrganizationService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetail (@PathVariable Long id){
        try {
            return new ResponseEntity<>(service.getDetail(id), HttpStatus.OK);
        }catch (IllegalStateException i){
            return new ResponseEntity<>(i.getMessage(),HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>("UNEXPECTED ERROR",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/{id}")
    public ResponseEntity<Long> update (@PathVariable Long id , @Valid @RequestBody OrganizationDTO dto){
        service.update(dto, id);
        return new ResponseEntity<>(id,HttpStatus.OK);
    }

}
