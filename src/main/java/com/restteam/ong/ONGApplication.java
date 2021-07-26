package com.restteam.ong;

import com.restteam.ong.models.Role;
import com.restteam.ong.models.User;
import com.restteam.ong.services.RoleService;
import com.restteam.ong.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ONGApplication{

    public static void main(String[] args) {
        SpringApplication.run(ONGApplication.class, args);
    }

}
