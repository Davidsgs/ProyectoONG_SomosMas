package com.restteam.ong.controllers;

import com.restteam.ong.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class OrganizationController {
    @Autowired
    OrganizationService service;

}
