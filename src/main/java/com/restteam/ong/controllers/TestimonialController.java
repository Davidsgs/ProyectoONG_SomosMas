package com.restteam.ong.controllers;

import com.restteam.ong.controllers.dto.TestimonialDto;
import com.restteam.ong.models.Testimonial;
import com.restteam.ong.services.TestimonialService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/testimonials")
public class TestimonialController {
    @Autowired
    private TestimonialService testimonialService;

    private ModelMapper modelMapper= new ModelMapper();

    @PostMapping("/")
    public ResponseEntity<?> saveTestimonial(@RequestBody TestimonialDto testimonialDto) {
        if(testimonialDto.getName() == null || testimonialDto.getImage() == null || testimonialDto.getContent() == null ){
            return new ResponseEntity<>("Request must contain name,image and Content values.", HttpStatus.BAD_REQUEST);
        }
        ResponseEntity responseEntity;
      try {
          var testimonial=modelMapper.map(testimonialDto,Testimonial.class);
          responseEntity=ResponseEntity.ok(testimonialService.createTestimonial(testimonial));
      }catch (Exception e){
          return  new ResponseEntity<>("Name or Content already exist try again",HttpStatus.BAD_REQUEST);
      }
      if (testimonialDto.getName().equals("") || testimonialDto.getContent().equals("")){
          return responseEntity= ResponseEntity.status(HttpStatus.FORBIDDEN).body("Name or Content is null  ");
        }
      return  responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeTestimonial(@RequestParam("id") Long id) {
        ResponseEntity respo;
        try {
            respo=ResponseEntity.ok(testimonialService.deleteSoft(id));
        }catch (Exception e){
            respo= ResponseEntity.status(HttpStatus.FORBIDDEN).body("Testimonial id does no exist");
        }
      return  respo;
    }

    @PutMapping("/testimonials/{id}")
    public ResponseEntity<TestimonialDto> updateTestimonial(@RequestBody TestimonialDto testimonialDto,@PathVariable("id") Long id){
        var testimonial =modelMapper.map(testimonialDto,Testimonial.class);
        return  new ResponseEntity(this.testimonialService.updateTestimonial(testimonial,id),HttpStatus.CREATED);
    }

}
