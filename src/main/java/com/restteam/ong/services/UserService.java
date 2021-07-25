package com.restteam.ong.services;

import com.restteam.ong.controllers.HtmlController;
import com.restteam.ong.controllers.dto.EmailRequest;
import com.restteam.ong.controllers.dto.UserDTO;
import com.restteam.ong.models.User;
import com.restteam.ong.models.impl.UserDetailsImpl;
import com.restteam.ong.repositories.UserRepository;
import com.sendgrid.Response;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
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
    EmailService emailService;

    @Autowired
    RoleService roleService;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    HtmlController htmlController;

    /// --- Método de Creación (Create) ---

    public User createUser(User user) {
        // no manda mail por la api
        //Verificamos de que no exista algún usuario ya registrado.
        if(!isValid(user)){
            throw new IllegalStateException("The email or the user is not valid. Try again.");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalStateException(String.format("Already exist an user with email %s", user.getEmail()));
        }
        if(!sendWelcomeMail(user)) {
            throw new UnsatisfiedDependencyException("The user was created, but the mail delivery failed.", "", "", "");
        }
            //Encriptamos la contraseña.
            user.setPassword(bcryptEncoder.encode(user.getPassword()));
            //Establecemos el TimeStamp de creación.
            user.setCreatedAt(System.currentTimeMillis() / 1000);
            return userRepository.save(user);


    }
    public boolean isValid(User user){
        return user.getFirstName() != null && user.getEmail() != null;
    }

    public boolean sendWelcomeMail(User user) {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(user.getEmail());
        emailRequest.setSubject("Contacto completado con exito.");
        emailRequest.setBody(htmlController.greeting(user.getFirstName(), htmlController.getModel()));
        Response emailResponse = emailService.sendTextEmail(emailRequest);

        return emailResponse.getStatusCode() == 200 || emailResponse.getStatusCode() == 202;
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
