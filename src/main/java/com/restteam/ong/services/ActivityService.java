package com.restteam.ong.services;

import java.util.ArrayList;
import java.util.Optional;

import com.restteam.ong.models.Activity;
import com.restteam.ong.repositories.ActivityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {
    @Autowired
    ActivityRepository activityRepository;


    public ArrayList<Activity> getActivities(){
        return (ArrayList<Activity>)this.activityRepository.findAll();
    }


//    public Optional<Activity> getActivityById(Long id){
//     return this.activityRepository.findById(id); //SI NO EXISTE DEVUELVE NULL
//    }



    public Activity getActivityById(Long id){
        return activityRepository.findById(id).orElseThrow(
                () -> new IllegalStateException(String.format("Testimonial with id %d doesn't exists.",id))
        );
    }


    public Optional<Activity> getActivityByName(String name){
        return this.activityRepository.findByName(name);//SI NO EXISTE DEVUELVE NULL
    }



    public Activity saveActivity(Activity activity) {
        return this.activityRepository.save(activity);
    }


    public String deleteActivity(Long id) {
        try {
            this.activityRepository.deleteById(id);
            return "Activity " + id + " successfully deleted";
        } catch (Exception e) {
            return "failed to delete activity " + id + "\nERROR\n\n" + e;
        }
    }
    public Boolean existId(Long id) {
        return activityRepository.existsById(id);
    }
}
