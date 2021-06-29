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
public class ONGApplication implements CommandLineRunner {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    public static void main(String[] args) {
        SpringApplication.run(ONGApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // -- CREACION DE ROLES POR DEFECTO --
        Role roleUser = new Role();
        roleUser.setName("ROLE_USER");
        roleUser.setDescription("Authenticated User Role.");

        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");
        roleAdmin.setDescription("Admin Role");

        //Verificamos que el rolUser no esté creado.
        try{
            roleUser = roleService.findByName(roleUser.getName());
        }
        //Al no estar creado lanzaría una excepcion y ahora lo creamos.
        catch (Exception e) {
            roleUser = roleService.createRole(roleUser);
        }

        //Lo mismo con lo de acá abajo.
        try{
            roleAdmin = roleService.findByName(roleAdmin.getName());
        } catch (Exception e){
            roleAdmin = roleService.createRole(roleAdmin);
        }

        // -- CREACION DE USUARIO POR DEFECTO --

        User user = new User();
        user.setFirstName("User");
        user.setLastName("Dev");
        user.setEmail("userDeveloper@email.com");
        user.setPassword("qwerty");
        user.setRole(roleAdmin);
        //Verificamos que no haya un usuario registrado con el mismo email.
        try{
            user = userService.findByEmail(user.getEmail());
        }
        //En caso de que no lo haya se atrapa la exepcion y se crea.
        catch (Exception e){
            user = userService.createUser(user);
        }

    }
}
