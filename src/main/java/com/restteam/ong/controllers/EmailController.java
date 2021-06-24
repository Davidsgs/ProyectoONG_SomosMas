package com.restteam.ong.controllers;

import com.restteam.ong.controllers.dto.EmailRequest;
import com.restteam.ong.services.EmailService;
import com.sendgrid.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(value = "/email")
@AllArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody EmailRequest emailRequest){
        Response response = emailService.sendTextEmail(emailRequest);
        if(response.getStatusCode() == 200 || response.getStatusCode() == 202){
            return new ResponseEntity<>("Sent successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to send", HttpStatus.BAD_REQUEST);
    }
}
