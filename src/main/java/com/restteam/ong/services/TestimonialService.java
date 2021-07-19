package com.restteam.ong.services;

import com.restteam.ong.controllers.dto.TestimonialDto;
import com.restteam.ong.controllers.dto.TestimonialPageResponse;
import com.restteam.ong.models.Testimonial;
import com.restteam.ong.repositories.TestimonialRepository;
import com.restteam.ong.services.util.EmptyRepositoryException;
import com.restteam.ong.services.util.PageEmptyException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
public class TestimonialService {
    @Autowired
    private TestimonialRepository testimonialRepository;

    ModelMapper modelMapper = new ModelMapper();

    public TestimonialPageResponse getTestimonial(Integer page) throws EmptyRepositoryException, PageEmptyException {
        if(testimonialRepository.findAll().isEmpty()){
            throw new EmptyRepositoryException("There are 0 registers of testimonials at the moment.");
        }
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<Testimonial> pageRequested =  testimonialRepository.findAll(pageRequest);
        if(pageRequested.getContent().isEmpty()){
            throw new PageEmptyException(String.format("Page not found, there are only %s pages.", pageRequested.getTotalPages()));
        }
        ArrayList<TestimonialDto> testimonialDtoArrayList = new ArrayList<>();
        pageRequested.forEach(
                (Testimonial testimonial) -> testimonialDtoArrayList.add(mapToDto(testimonial))
        );
        // Una vez que obtengo la pagina y la mapeo en una lista de DTOs
        // lo siguiente es armar la response
        TestimonialPageResponse testimonialPageResponse = new TestimonialPageResponse();
        testimonialPageResponse.setTestimonials(testimonialDtoArrayList);
        // Aca me tengo que fijar si tienen pagina siguiente o previa para incluir los links
        if(pageRequested.hasNext()){
            testimonialPageResponse.setNextPageUrl(String.format("/testimonials?page=%s", page + 1));
        }
        if(pageRequested.hasPrevious()){
            testimonialPageResponse.setPreviousPageUrl(String.format("/testimonials?page=%s", page - 1));
        }
        return testimonialPageResponse;
    }

    public Testimonial findById(Long id){
        return testimonialRepository.findById(id).orElseThrow(
                () -> new IllegalStateException(String.format("Testimonial with id %d doesn't exists.",id))
        );
    }

    //Guardar Testimonio--
    public Testimonial createTestimonial(Testimonial testimonialCreate) {

        //Verifico que el Testimonio no exista
        if (testimonialRepository.findByName(testimonialCreate.getName()).isPresent() && testimonialRepository.findByContent(testimonialCreate.getContent()).isPresent()){
            throw new IllegalStateException(String.format(" Name or Content already exist try again",testimonialCreate.getName(), testimonialCreate.getContent()));
        }

        // si no son iguales crea el testimonio
        testimonialCreate.setCreatedAt(System.currentTimeMillis() / 1000);
        return testimonialRepository.save(testimonialCreate);
    }

    //--Metodo de Actulizar--
    public Object updateTestimonial(Testimonial testimonialUpdate, Long id) {
        Testimonial testimo = testimonialRepository.findById(id).orElse(null);
        try {

            testimo.setContent(testimonialUpdate.getContent());
            testimo.setUpdatedAt(System.currentTimeMillis() / 1000);
            testimo.setImage(testimonialUpdate.getImage());
            testimo.setName(testimonialUpdate.getName());
            testimonialRepository.save(testimo);
        } catch (Exception e) {
            return ("i do not know update, Id does not exist");
        }
        return  testimo;
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

    private TestimonialDto mapToDto(Testimonial testimonial) {
        return modelMapper.map(testimonial, TestimonialDto.class);
    }
}