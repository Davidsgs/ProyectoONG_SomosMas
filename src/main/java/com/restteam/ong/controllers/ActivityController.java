package com.restteam.ong.controllers;

import java.util.Date;

import javax.validation.Valid;

import com.restteam.ong.controllers.dto.ActivityRequest;
import com.restteam.ong.models.Activity;
import com.restteam.ong.services.ActivityService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    ActivityService activityService;
    ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<?> postActivity(@Valid @RequestBody ActivityRequest activity) {
        Activity myActivity = new Activity();
        modelMapper.map(activity, myActivity);

        myActivity.setDeleted(false);
        myActivity.setCreatedAt(new Date().getTime());

        Activity activityOutput = this.activityService.postActivity(myActivity);

        return ResponseEntity.ok(activityOutput);
    }
}
