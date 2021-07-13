package com.restteam.ong.controllers;


import com.restteam.ong.models.Slide;
import com.restteam.ong.services.SlideService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@AllArgsConstructor
public class SlideController {

    private final SlideService slideService;

    ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<String> addSlide(@RequestBody Slide slide){
        try{
            slideService.addSlide(slide);
            return new ResponseEntity<>("El slice se agrego correctamente", HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity< >("Error: No se pudo crear el slice",HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteSlide(@PathVariable("id") Long slideId){
        try{
           slideService.deleteSlide(slideId);
           return new ResponseEntity<>("Slide deteled successfully.",HttpStatus.OK);
        }
        catch(Exception ex){
           return new ResponseEntity<>("Slide wasn't found.",HttpStatus.BAD_REQUEST);
        }
    }





    private SlideDTO mapToDto(Slide slide){
        return modelMapper.map(slide,SlideDTO.class);
    }

    private Slide mapToClass(SlideDTO slideDTO,Slide slide){
        modelMapper.map(slideDTO,slide);
        return slide;
    }
}
