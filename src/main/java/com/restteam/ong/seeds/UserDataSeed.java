package com.restteam.ong.seeds;

import com.restteam.ong.models.Role;
import com.restteam.ong.models.User;
import com.restteam.ong.services.RoleService;
import com.restteam.ong.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDataSeed implements CommandLineRunner {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Override
    public void run(String... args) throws Exception {
        createDefaultRolesIfNotExist();
        generateRegisteredUsersSeedIfNotExist();
        generateAdminUsersSeedIfNotExist();
    }

    /**
     * -- CREACION DE ROLES POR DEFECTO --
     * Se revisa si los roles existen, en caso contrario los crea.
     */
    public void createDefaultRolesIfNotExist(){
        //Rol de Usuario Registrado.
        Role roleUser = new Role();
        roleUser.setName("ROLE_USER");
        roleUser.setDescription("Authenticated User Role.");
        //Rol de administrador.
        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");
        roleAdmin.setDescription("Admin Role");
        //Creamos un Array y metemos los roles allí.
        var rolesToCreate = new ArrayList<Role>();
        rolesToCreate.addAll(List.of(roleAdmin,roleUser));
        //Revisamos cada elemento del array y lo creamos si es necesario.
        for (Role role: rolesToCreate) {
            checkIfRoleExistElseCreateNew(role);
        }
    }

    /**
     * Metodo que chequea si el role pasado por parametro existe, de lo contrario lo registra en la base de datos.
     * @param role El rol al que queremos chequear si existe o de lo contrario craerlo.
     * @return Role
     */
    public void checkIfRoleExistElseCreateNew(Role role){
        try{
            roleService.findByName(role.getName());
        } catch (Exception e){
            roleService.createRole(role);
        }
    }

    /**
     * -- CREACION DE LOS 10 USUARIOS ADMIN  --
     * Se revisa si los usuarios existen, en caso contrario los crea.
     */
    private void generateAdminUsersSeedIfNotExist() {
        var firstNameBase = "UserAdminNumber%d";
        var emailBase = "adminUser%d@email.com";
        var lastNameBase = "AdminTest";
        var basePassword = "qwerty";
        var roleBase = roleService.findByName("ROLE_ADMIN"); //Rol de usuario registrado.
        if(!userService.userExistsByEmail(String.format(emailBase,1))){
            createUsers(emailBase,firstNameBase,lastNameBase,basePassword,roleBase);
        }
    }

    /**
     * -- CREACION DE LOS 10 USUARIOS REGISTRADOS  --
     * Se revisa si los usuarios existen, en caso contrario los crea.
     */
    public void generateRegisteredUsersSeedIfNotExist(){
        var firstNameBase = "UserNumber%d";
        var emailBase = "registeredUser%d@email.com";
        var lastNameBase = "Test";
        var basePassword = "qwerty";
        var roleBase = roleService.findByName("ROLE_USER"); //Rol de usuario Administrador.
        if(!userService.userExistsByEmail(String.format(emailBase,1))){
            createUsers(emailBase,firstNameBase,lastNameBase,basePassword,roleBase);
        }
    }

    /**
     * Metodo que crea 10 usuarios con los datos que les mandamos por parametro.
     * @param emailBase Es el email que queremos que tengan los usuario a crear. (Debe ser un texto compatible con el String.format())
     * @param firstNameBase Es el nombre que queremos que tengan los usuario. (Debe ser un texto compatible con el String.format())
     * @param lastNameBase Es el apellido que queremos que tengan los usuario. (Debe ser un texto compatible con el String.format())
     * @param basePassword La contraseña que queremos que tengan los usuarios. (Debe ser un texto compatible con el String.format())
     * @param roleBase Es el rol que queremos que tengan los usuarios a crear.
     */
    private void createUsers(String emailBase, String firstNameBase,String lastNameBase, String basePassword, Role roleBase){
        for(int i = 1; i <= 10; i++){
            User userAux = new User();
            userAux.setFirstName(String.format(firstNameBase,i));
            userAux.setLastName(lastNameBase);
            userAux.setEmail(String.format(emailBase,i));
            userAux.setPassword(basePassword);
            userAux.setRole(roleBase);
            checkIfUserExistElseCreateNew(userAux);
        }
    }

    /**
     * Metodo que chequea si el usuario pasado por parametro existe, de lo contrario lo registra en la base de datos.
     * @param user el usuario al que queremos chequear si existe o de lo contrario craerlo.
     * @return User
     */
    public void checkIfUserExistElseCreateNew(User user){
        if(!userService.userExistsByEmail(user.getEmail())){
            userService.createUser(user);
        }
    }
}

