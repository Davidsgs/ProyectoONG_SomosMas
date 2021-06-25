package com.restteam.ong.services;

import java.util.ArrayList;
import java.util.Optional;

//import com.restteam.ong.controllers.dto.ActivityDTO;
import com.restteam.ong.models.Activity;
import com.restteam.ong.repositories.ActivityRepository;

//import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {
    @Autowired
    ActivityRepository activityRepository;
  /*  ModelMapper modelMapper = new ModelMapper();

    public ArrayList<ActivityDTO> getActivities() {
        ArrayList<Activity> activities = (ArrayList<Activity>) this.activityRepository.findAll();
        ArrayList<ActivityDTO> activitiesDTO = new ArrayList<>();

        for (int i = 0; i < activities.size(); i++) {
            ActivityDTO aux = new ActivityDTO();
            modelMapper.map(activities.get(i), aux);
            activitiesDTO.add(aux);
        }
        return activitiesDTO;
    }*/

    public ArrayList<Activity> getActivities(){
        return (ArrayList<Activity>)this.activityRepository.findAll();
    }


    public Optional<Activity> getActivityById(Long id){
      return this.activityRepository.findById(id); //SI NO EXISTE DEVUELVE NULL
    }

/*
    public Optional<ActivityDTO> getActivityByName(String name) {
        Optional<Activity> activity = activityRepository.findByName(name);

        if (activity != null) {
            ActivityDTO activityDTO = new ActivityDTO();
            modelMapper.map(activity, activityDTO);
            return Optional.of(activityDTO);
        } else {
            return null;
        }
    }
*/

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
}
