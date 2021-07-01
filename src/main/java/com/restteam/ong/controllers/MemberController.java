package com.restteam.ong.controllers;


import com.restteam.ong.controllers.dto.MemberDTO;
import com.restteam.ong.models.Member;
import com.restteam.ong.services.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/members")
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping()
    public ResponseEntity<?> addMember(@RequestBody Member member){
        try{
            Long memberId = memberService.addMember(member).orElseThrow(() -> new IllegalStateException(""));
            return new ResponseEntity<Long>(memberId,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<String>("Error: No se pudo crear al miembro.",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public List<MemberDTO> getMembers(){
        return memberService.getMembers();
    }

    @PutMapping(path = "{memberId}")
    public ResponseEntity<String> updateMember(@PathVariable Long memberId,
                                               @RequestParam(required = false) String name,
                                               @RequestParam(required = false) String facebook,
                                               @RequestParam(required = false) String instagram,
                                               @RequestParam(required = false) String linkedin,
                                               @RequestParam(required = false) String image,
                                               @RequestParam(required = false) String description){
        try{
            Member member = memberService.updateMember(memberId,name,facebook,instagram,linkedin,image,description).
                    orElseThrow(() -> new IllegalStateException(""));
            return new ResponseEntity<String>("El miembro se ha actualizado!",HttpStatus.OK);
        }
        catch(Exception e){ return new ResponseEntity<String>("ERROR: No se pudo actualizar.",HttpStatus.BAD_REQUEST); }
    }



    @DeleteMapping(path = "{memberId}")
    public ResponseEntity<String> deleteMember(@PathVariable Long memberId){
        try{
            Long deleted = memberService.deleteMember(memberId).orElseThrow(() -> new IllegalStateException(""));
            return new ResponseEntity<String>("Miembro borrado.",HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<String>("No se ha podido borrar al miembro solicitado.",HttpStatus.BAD_REQUEST);
        }
    }
}