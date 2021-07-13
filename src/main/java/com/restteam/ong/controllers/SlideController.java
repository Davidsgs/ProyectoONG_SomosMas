package com.restteam.ong.controllers;


import com.restteam.ong.controllers.dto.SimpleSlideDTO;
import com.restteam.ong.controllers.dto.SlideDTO;
import com.restteam.ong.models.Slide;
import com.restteam.ong.services.SlideService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping
@AllArgsConstructor
public class SlideController {

    private final SlideService slideService;

    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<String> addSlide(@RequestBody Slide slide) {
        try {
            slideService.addSlide(slide);
            return new ResponseEntity<>("El slice se agrego correctamente", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error: No se pudo crear el slice", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteSlide(@PathVariable("id") Long slideId) {
        try {
            slideService.deleteSlide(slideId);
            return new ResponseEntity<>("Slide deteled successfully.", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Slide wasn't found.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> PutSlide(@PathVariable("id") Long id, @Valid @RequestBody SlideDTO slideDTO) {
        ResponseEntity response;
        try {
            var slide = this.mapToClass(slideDTO, slideService.getSlideById(id));
            response = ResponseEntity.ok(slide);
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return response;
    }

    @GetMapping()
    public ResponseEntity<?> getAllSlides() {
        var slides = slideService.getAllSlides();
        var slidesDTO = slides.stream()
                .map(
                        slide -> modelMapper.map(slide, SimpleSlideDTO.class)
                ).collect(Collectors.toList());
        return ResponseEntity.ok(slidesDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity getSlideById(Long id) {
        ResponseEntity response;
        try {
            response = ResponseEntity.ok(slideService.getSlideById(id));
        } catch (IllegalStateException e) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            response = ResponseEntity.badRequest().body(e.getMessage());
        }
        return response;
    }

    private SlideDTO mapToDto(Slide slide) {
        return modelMapper.map(slide, SlideDTO.class);
    }

    private Slide mapToClass(SlideDTO slideDTO, Slide slide) {
        modelMapper.map(slideDTO, slide);
        return slide;
    }
}
