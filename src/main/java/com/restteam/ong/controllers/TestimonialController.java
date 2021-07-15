package com.restteam.ong.controllers;

import com.restteam.ong.controllers.dto.TestimonialDto;
import com.restteam.ong.models.Testimonial;
import com.restteam.ong.services.TestimonialService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testimonials")
public class TestimonialController {
    @Autowired
    private TestimonialService testimonialService;

    private ModelMapper modelMapper= new ModelMapper();

    @PostMapping()
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
    public ResponseEntity removeTestimonial(@PathVariable("id") Long id) {
        ResponseEntity respo;
        try {
            respo=ResponseEntity.ok(testimonialService.deleteSoft(id));
        }catch (Exception e){
            respo= ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Testimonial id does no exist");
        }
      return  respo;
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestimonialDto> updateTestimonial(@RequestBody TestimonialDto testimonialDto,@PathVariable("id") Long id){
        ResponseEntity respoUp;

        try {

            var testimonial = modelMapper.map(testimonialDto, Testimonial.class);
             respoUp= ResponseEntity.ok(testimonialService.updateTestimonial(testimonial,id));
        }
        catch (Exception e){
            respoUp= ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Testimonial id does no exist");
        }
        if (id<=1 || id ==null){
            return new ResponseEntity("Request must contain name,image and Content values.", HttpStatus.BAD_REQUEST);
        }
        return respoUp;
    }

}
