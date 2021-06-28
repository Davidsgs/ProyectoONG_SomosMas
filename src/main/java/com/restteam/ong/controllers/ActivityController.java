package com.restteam.ong.controllers;

import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import com.restteam.ong.controllers.dto.ActivityRequest;
import com.restteam.ong.models.Activity;
import com.restteam.ong.services.ActivityService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    ActivityService activityService;
    ModelMapper modelMapper = new ModelMapper();

    @PostMapping
    public ResponseEntity<?> postActivity(@Valid @RequestBody ActivityRequest activity) {

        if (activity.getName().equals("") || activity.getContent().equals("")) {// EVITA CADENAS VACIAS NO NULL
            return ResponseEntity.badRequest().header("Failure", "name and content can not be \"\"")
                    .body("{\"error\":\"inputError\",\"detail\":\"" + activity
                            + " \"name\" and \"content\" can not be (\"\")}");
        }
        Activity myActivity = new Activity();
        modelMapper.map(activity, myActivity);

        myActivity.setDeleted(false);
        myActivity.setCreatedAt(new Date().getTime());

        Activity activityOutput = this.activityService.saveActivity(myActivity);

        return ResponseEntity.ok(activityOutput);
    }


    
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateActivity(@Valid @RequestBody ActivityRequest activity, @PathVariable Long id) {

        if (activity.getName().equals("") || activity.getContent().equals("")) {// EVITA CADENAS VACIAS NO NULL
            return ResponseEntity.badRequest().header("Failure", "name and content can not be \"\"")
                    .body("{\"error\":\"inputError\",\"detail\":\"" + activity
                            + " \"name\" and \"content\" can not be (\"\")}");
        }

        Optional<Activity> activityOptional = this.activityService.getActivityById(id);

        if (!activityOptional.isPresent()) {
            return ResponseEntity.notFound().build();

        } else {
            Activity myActivity = activityOptional.get();
            modelMapper.map(activity, myActivity);
            myActivity.setId(id);
            myActivity.setUpdatedAt(new Date().getTime());
            Activity activityOutput = this.activityService.saveActivity(myActivity);

            return ResponseEntity.ok(activityOutput);
        }
    }
}
