package com.restteam.ong.controllers;

import com.restteam.ong.controllers.dto.AuthenticationRequest;
import com.restteam.ong.controllers.dto.AuthenticationResponse;
import com.restteam.ong.controllers.dto.UserDTO;
import com.restteam.ong.models.User;
import com.restteam.ong.models.impl.UserDetailsImpl;
import com.restteam.ong.services.impl.UserDetailsServiceImpl;
import com.restteam.ong.util.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private ModelMapper modelMapper = new ModelMapper();

    @PostMapping(path = "/login")
    public ResponseEntity<?> createAthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e){
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());//En el userService se verifica que no se esté solicitando un usuario registrado.

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping(path = "/register")
    ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        ResponseEntity response;
        try{
            var user = modelMapper.map(userDTO, User.class);
            response = ResponseEntity.ok(userDetailsService.registerUser(user));
        }catch (Exception e){
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return response;
    }

    @GetMapping(path = "/me")
    ResponseEntity<?> getUserInfo(@AuthenticationPrincipal UserDetailsImpl user){
        return ResponseEntity.ok(user.getUser());
    }

}