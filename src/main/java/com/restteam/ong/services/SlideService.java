package com.restteam.ong.services;

import com.restteam.ong.models.Slide;

import java.util.ArrayList;

public interface SlideService {
    void addSlide(Slide slide);

    public Slide getSlideById(Long id);

    public void deleteSlide(Long slideId);

    public ArrayList<Slide> getAllSlides();
}
