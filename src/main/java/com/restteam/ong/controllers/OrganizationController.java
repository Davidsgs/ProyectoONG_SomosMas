package com.restteam.ong.controllers;

import javax.validation.Valid;

import com.restteam.ong.controllers.dto.OrganizationCreateDTO;
import com.restteam.ong.controllers.dto.OrganizationDTO;
import com.restteam.ong.services.OrganizationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/organization/public")
public class OrganizationController {

    public static final String UNEXPECTED_ERROR = "UNEXPECTED ERROR";

    @Autowired
    OrganizationService service;

    public OrganizationController(OrganizationService orgServMok) {
        this.service=orgServMok;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetail(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(service.getDetail(id), HttpStatus.OK);
        } catch (IllegalStateException i) {
            return new ResponseEntity<>(i.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(UNEXPECTED_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody OrganizationDTO dto) {
        try {
            service.update(dto, id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (IllegalStateException i) {
            return new ResponseEntity<>(i.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(UNEXPECTED_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody OrganizationCreateDTO dto) {
        try {
            return new ResponseEntity<>(service.create(dto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(UNEXPECTED_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
