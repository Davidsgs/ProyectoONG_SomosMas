package com.restteam.ong.services;

import com.restteam.ong.models.Testimonial;
import com.restteam.ong.repositories.TestimonialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TestimonialService {
    @Autowired
    private TestimonialRepository testimonialRepository;


    public ArrayList<Testimonial> GetTestimonial() {

        return (ArrayList<Testimonial>) testimonialRepository.findAll();
    }

    public Testimonial saveTestimonial(Testimonial testimonialCreate) {
        Testimonial testimoCreate = new Testimonial();
        testimoCreate.setCreatedAt(System.currentTimeMillis()/1000);
        return testimonialRepository.save(testimonialCreate);
    }

    public String upDateTestimonial(Testimonial testimonialUpdate) {
        try {
            Testimonial testimo = testimonialRepository.findById(testimonialUpdate.getId()).orElse(null);
            testimo.setContent(testimonialUpdate.getContent());
            testimo.setUpdatedAt(System.currentTimeMillis() / 1000);
            testimo.setImage(testimonialUpdate.getImage());
            testimo.setName(testimonialUpdate.getName());
        } catch (Exception e) {
            return ("i do not know update, Id does not exist");
        }
        return  "Testimonial Update";
    }

    public String deleteSoftDelete(Long id) {
        try {
            testimonialRepository.deleteById(id);
        }catch (Exception e) {
            return "Testimonial does not exist";
        }
        return "Testimonial deleted";

    }
}