package com.restteam.ong.services;

import com.restteam.ong.controllers.dto.TestimonialDto;
import com.restteam.ong.models.Testimonial;
import com.restteam.ong.repositories.TestimonialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
public class TestimonialService {
    @Autowired
    private TestimonialRepository testimonialRepository;

//-*-Trae todos los testimonios-*-//
    public ArrayList<Testimonial> getTestimonial() {

        return (ArrayList<Testimonial>) testimonialRepository.findAll();
    }

    //Guardar Testimonio--
    public Testimonial createTestimonial(Testimonial testimonialCreate) {
        //Verifico que el Testimonio no exista
        if (testimonialRepository.findByName(testimonialCreate.getName()).isPresent() && testimonialRepository.findByContent(testimonialCreate.getContent()).isPresent()){
            throw new IllegalStateException(String.format(" Name or Content already exist try again",testimonialCreate.getName(), testimonialCreate.getContent()));

        }
        // si no existe crea el testimonio
        testimonialCreate.setCreatedAt(System.currentTimeMillis() / 1000);
        return testimonialRepository.save(testimonialCreate);
    }

    //--Metodo de Actulizar--

    public String updateTestimonial(Testimonial testimonialUpdate) {
        try {
            Testimonial testimo = testimonialRepository.findById(testimonialUpdate.getId()).orElse(null);
            testimo.setContent(testimonialUpdate.getContent());
            testimo.setUpdatedAt(System.currentTimeMillis() / 1000);
            testimo.setImage(testimonialUpdate.getImage());
            testimo.setName(testimonialUpdate.getName());
            testimonialRepository.save(testimo);
        } catch (Exception e) {
            return ("i do not know update, Id does not exist");
        }
        return  "Testimonial Update";
    }
    @Transactional
    public String deleteSoft(Long id) {
        try {
            testimonialRepository.deleteById(id);
        }catch (Exception e){
            return ("Testimonial does no exist");
        }
        return "Testimonial deleted";
    }
}