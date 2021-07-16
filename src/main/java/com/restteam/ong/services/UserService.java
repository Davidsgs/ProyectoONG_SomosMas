package com.restteam.ong.services;

import com.restteam.ong.controllers.dto.UserDTO;
import com.restteam.ong.models.User;
import com.restteam.ong.models.impl.UserDetailsImpl;
import com.restteam.ong.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    /// --- Método de Creación (Create) ---

    public User createUser(User user) {
        //Verificamos de que no exista algún usuario ya registrado.

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalStateException(String.format("Already exist an user with email %s", user.getEmail()));
        }
        //Encriptamos la contraseña.
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        //Establecemos el TimeStamp de creación.
        user.setCreatedAt(System.currentTimeMillis() / 1000);
        return userRepository.save(user);
    }

    // --- Métodos de Lectura (Read) ---

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new IllegalStateException(String.format("User with id: %d not found", id))
        );
    }

    public ArrayList<User> findAll(){
        return (ArrayList<User>) userRepository.findAll();
    }


    //Buscar por Email, en caso de que no lo encuentre lanza un IllegalStateException.
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalStateException(String.format("User with email: %s not found.", email))
        );
    }

    // --- Método de Actualizar (Update) ---

    @Transactional
    public User updateUser(Long id, UserDTO user) {
        var userToUpdate = findById(id);
        if (user.getFirstName() != null && !user.getFirstName().isBlank() ) {
            userToUpdate.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null && !user.getLastName().isBlank()) {
            userToUpdate.setLastName(user.getLastName());
        }
        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            userToUpdate.setEmail(user.getEmail());
        }
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            userToUpdate.setPassword(bcryptEncoder.encode(user.getPassword()));
        }
        if (user.getPhoto() != null && !user.getPhoto().isBlank()) {
            userToUpdate.setPhoto(user.getPhoto());
        }
        if (user.getRole() != null){
            userToUpdate.setRole(user.getRole());
        }
        //Se agrega la ultima vez que fue actualizado.
        userToUpdate.setUpdatedAt(System.currentTimeMillis() / 1000);
        return userToUpdate;
    }

    // --- Método de Borrado (Delete) ---

    public Boolean deleteUser(Long id) {
        try {
            var userToDelete = findById(id);
            userRepository.delete(userToDelete);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean userCanModifyUserWithId(UserDetailsImpl userDetailsImpl, Long id) {
        var bool = userDetailsImpl.getUser().getRole().getName().contentEquals("ROLE_ADMIN");
        if (!bool) {
            bool = userDetailsImpl.getUser().getId().equals(id);
        }
        return bool;
    }

}
