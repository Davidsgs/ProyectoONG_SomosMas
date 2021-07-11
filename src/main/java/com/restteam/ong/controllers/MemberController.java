package com.restteam.ong.controllers;

import java.util.List;

import com.restteam.ong.controllers.dto.MemberDTO;
import com.restteam.ong.models.Member;
import com.restteam.ong.services.MemberService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/members")
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping()
    public ResponseEntity<?> addMember(@RequestBody Member member) {
        try {
            Long memberId = memberService.addMember(member).orElseThrow(() -> new IllegalStateException(""));
            return new ResponseEntity<Long>(memberId, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Error: No se pudo crear al miembro.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public List<MemberDTO> getMembers() {
        return memberService.getMembers();
    }

    @PutMapping(path = "{memberId}")
    public ResponseEntity<String> updateMember(@PathVariable Long memberId, @RequestParam(required = false) String name,
            @RequestParam(required = false) String facebook, @RequestParam(required = false) String instagram,
            @RequestParam(required = false) String linkedin, @RequestParam(required = false) String image,
            @RequestParam(required = false) String description) {
        try {
            memberService.updateMember(memberId, name, facebook, instagram, linkedin, image, description)
                    .orElseThrow(() -> new IllegalStateException(""));
            return new ResponseEntity<String>("El miembro se ha actualizado!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("ERROR: No se pudo actualizar.", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "{memberId}")
    public ResponseEntity<String> deleteMember(@PathVariable Long memberId) {
        try {
            memberService.deleteMember(memberId).orElseThrow(() -> new IllegalStateException(""));
            return new ResponseEntity<String>("Miembro borrado.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("No se ha podido borrar al miembro solicitado.", HttpStatus.BAD_REQUEST);
        }
    }
}
