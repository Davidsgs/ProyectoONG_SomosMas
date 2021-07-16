package com.restteam.ong.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.restteam.ong.controllers.dto.ActivityRequest;
import com.restteam.ong.models.Activity;
import com.restteam.ong.services.ActivityService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    ActivityService activityService;
    ModelMapper modelMapper = new ModelMapper();

    @PostMapping
    public ResponseEntity<?> createActivity(@Valid @RequestBody ActivityRequest activity) {

        Activity myActivity = new Activity();
        modelMapper.map(activity, myActivity);

        myActivity.setDeleted(false);
        myActivity.setCreatedAt(new Date().getTime());
        myActivity.setUpdatedAt(new Date().getTime());

        Activity activityOutput = this.activityService.saveActivity(myActivity);

        return ResponseEntity.ok(activityOutput);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateActivity(@Valid @RequestBody ActivityRequest activity, @PathVariable Long id) {

        Activity activityOptional = this.activityService.getActivityById(id);

        if (activityService.existId(id)) {
            return ResponseEntity.notFound().build();

        } else {
            Activity myActivity = new Activity();
            modelMapper.map(activity, myActivity);
            myActivity.setId(id);
            myActivity.setUpdatedAt(new Date().getTime());
            Activity activityOutput = this.activityService.saveActivity(myActivity);

            return ResponseEntity.ok(activityOutput);
        }
    }



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
