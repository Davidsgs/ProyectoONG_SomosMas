package com.restteam.ong.controllers;

import com.restteam.ong.controllers.dto.TestimonialDto;
import com.restteam.ong.models.Testimonial;
import com.restteam.ong.services.TestimonialService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/testimonials")
public class TestimonialController {
    @Autowired
    private TestimonialService testimonialService;

    private ModelMapper modelMapper= new ModelMapper();

    @PostMapping("/")
    public ResponseEntity<?> saveTestimonial(@RequestBody TestimonialDto testimonialDto) {
      ResponseEntity responseEntity;
      try {
          var testimonial=modelMapper.map(testimonialDto,Testimonial.class);
          responseEntity=ResponseEntity.ok(testimonialService.createTestimonial(testimonial));
      }catch (Exception e){
          responseEntity=ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
      }

    return  responseEntity;
    }

    @DeleteMapping("/:{id}")
    public ResponseEntity removeTestimonial(@RequestParam("id") Long id) {
        ResponseEntity respo;
        try {
            respo=ResponseEntity.ok(testimonialService.deleteSoft(id));
        }catch (Exception e){
            respo= ResponseEntity.status(HttpStatus.FORBIDDEN).body("Testimonial does no exist");
        }
      return  respo;
    }

    @PutMapping("/testimonials/:{id}")
    public ResponseEntity<TestimonialDto> updateTestimonial(@RequestBody TestimonialDto testimonialDto){
        var testimonial =modelMapper.map(testimonialDto,Testimonial.class);
        return  new ResponseEntity(this.testimonialService.updateTestimonial(testimonial),HttpStatus.CREATED);
    }

}
