package com.restteam.ong.services.impl;

import com.restteam.ong.models.Slide;
import com.restteam.ong.services.SlideService;

public class SlideServiceImpl implements SlideService {


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
}
