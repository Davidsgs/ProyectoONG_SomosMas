package com.restteam.ong.controllers;

import com.restteam.ong.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RoleController {

    @Autowired
    RoleService roleService;
}
