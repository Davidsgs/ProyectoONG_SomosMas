package com.restteam.ong.services.impl;

import java.util.ArrayList;

import javax.transaction.Transactional;

import com.restteam.ong.controllers.dto.SlideDTO;
import com.restteam.ong.models.Slide;
import com.restteam.ong.repositories.SlideRepository;
import com.restteam.ong.services.SlideService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SlideServiceImpl implements SlideService {

    @Autowired
    SlideRepository slideRepository;

    private final String SLIDE_NOT_FOUND = "Slide with id: %d not found";

    @Override
    public void addSlide(Slide slide) {
        //TODO: pasar imagen de string a Byte[].
  
        slideRepository.save(slide);
    }


    @Override
    public void deleteSlide(Long slideID){
        boolean exists = slideRepository.existsById(slideID);
        if(!exists){
            throw new IllegalStateException("");
        }
        slideRepository.deleteById(slideID);
    }


    @Override
    public Slide getSlideById(Long id) {
        return slideRepository.findById(id).orElseThrow(
                () -> new IllegalStateException(String.format(SLIDE_NOT_FOUND,id))
        );
    }

    @Override
    public ArrayList<Slide> getAllSlides() {
        return (ArrayList<Slide>) slideRepository.findAll();
    }

    @Override
    @Transactional
    public Object updateSlide(Long id, SlideDTO slide){
        var slideToUpdate = this.getSlideById(id);
        var organization = this.getSlideById(id).getOrganizationId();
        slideToUpdate.setOrganizationId(organization);
        if (slide.getImageUrl() != null && !slide.getImageUrl().isBlank()) {
            slideToUpdate.setImageUrl(slide.getImageUrl());
        }
        if (slide.getText() != null && !slide.getText().isBlank()) {
            slideToUpdate.setText(slide.getText());
        }

            slideToUpdate.setNumberOrder(slide.getNumberOrder());


        return  slideToUpdate;
    }

    @Override
    public ArrayList<Slide> getAllSlidesByOrganizationId(Long id){        
        return this.slideRepository.findByOrganizationId_IdOrderByNumberOrderAsc(id);
    }

    @Override
    public void orderSlides(Long orgId){
        ArrayList<Slide> slides= this.getAllSlidesByOrganizationId(orgId);
    //    ArrayList<Slide> newSlides= new ArrayList<>();

        for(int i=0; i<slides.size(); i++){
            if(slides.get(i).getNumberOrder()>i+1){
                slides.get(i).setNumberOrder(i+1);
                this.slideRepository.save(slides.get(i));
            }
        } 
    }



}
