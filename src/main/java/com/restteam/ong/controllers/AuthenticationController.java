package com.restteam.ong.controllers;

import javax.validation.Valid;

import com.restteam.ong.controllers.dto.AuthenticationRequest;
import com.restteam.ong.controllers.dto.AuthenticationResponse;
import com.restteam.ong.controllers.dto.UserRegisterRequest;
import com.restteam.ong.models.User;
import com.restteam.ong.models.impl.UserDetailsImpl;
import com.restteam.ong.services.RoleService;
import com.restteam.ong.services.impl.UserDetailsServiceImpl;
import com.restteam.ong.util.BindingResultsErrors;
import com.restteam.ong.util.JwtUtil;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private RoleService roleService;

    private ModelMapper modelMapper = new ModelMapper();

    @PostMapping(path = "/login")
    public ResponseEntity<?> createAthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        ResponseEntity<?> responseEntity;
        try{
            //En el userService se verifica que no se esté solicitando un usuario registrado.
            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(authenticationRequest.getUsername());
            //Y en el AuthenticationManager que las credenciales sean correctas.
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
            //Si está ok se le asigna un JWT al user.
            final String jwt = jwtUtil.generateToken(userDetails);
            //Y se retorna.
            responseEntity = ResponseEntity.ok(new AuthenticationResponse(jwt));
        } catch (IllegalStateException e){
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).body("Incorrect Password");
        }
        return responseEntity;
    }

    @PostMapping(path = "/register")
    ResponseEntity<?> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest,
                               @Parameter(hidden = true) BindingResult bindingResult) { //Ocultamos para que no se vea en swagger.
        ResponseEntity<?> response;
        if(bindingResult.hasErrors()){ //Se revisa si no hay errores del @Valid.
            response = BindingResultsErrors.getResponseEntityWithErrors(bindingResult); //Se mandan a traer un ResponseEntity que contenga los errores.
        }
        //Si no hay errores entonces:
        else {
            try {
                //Buscamos el rol por defecto de los usuarios registrados.
                var roleName = "ROLE_USER";
                var roleUser = roleService.findByName(roleName);
                //Creamos el Usuario a registrar
                var user = modelMapper.map(userRegisterRequest, User.class);
                //Le agregamos el Role de Usuario registrado.
                user.setRole(roleUser);
                //Guardamos la contraseña sin encriptar para luego poder iniciar sesion una vez registrado.
                var password = user.getPassword();
                //Lo registramos
                userDetailsService.registerUser(user);
                //Creamos la authentication request para poder logearnos en el sistema.
                var authRequest = new AuthenticationRequest();
                authRequest.setUsername(user.getEmail());
                authRequest.setPassword(password);
                //Iniciamos sesion para recibir el JWT y devolverlo.
                response = createAthenticationToken(authRequest);
            } catch (Exception e) {
                response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
        return response;
    }

    @GetMapping(path = "/me")
    ResponseEntity<?> getUserInfo(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UserDetailsImpl user){
        return ResponseEntity.ok(user.getUser());
    }
}