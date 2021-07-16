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
        if(slide.getNumberOrder() == null){
            try{
                slide.setNumberOrder(lastSlideOfDB().getNumberOrder());
            }catch(Exception ex){
                slide.setNumberOrder(0);
            }
        }
        slideRepository.save(slide);
    }

    //Si lo van a usar tener cuidado, tira error.
    private Slide lastSlideOfDB(){ return slideRepository.findTopByOrderByIdDesc().orElseThrow(
            () -> new IllegalStateException("There's no slides on database.")); }

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
        ArrayList<Slide> aux= new ArrayList<>();
        
        aux= this.slideRepository.findByOrganizationIdOrderByNumberOrderAsc(id);
        return aux;
    }



}